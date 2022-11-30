package com.enfint.dealservice.service.impl;

import com.enfint.dealservice.dto.CreditDTO;
import com.enfint.dealservice.dto.LoanOfferDTO;
import com.enfint.dealservice.dto.ScoringDataDTO;
import com.enfint.dealservice.entity.Application;
import com.enfint.dealservice.entity.Client;
import com.enfint.dealservice.entity.Credit;
import com.enfint.dealservice.exception.ApplicationNotFoundException;
import com.enfint.dealservice.feignclient.ConveyorClient;
import com.enfint.dealservice.repository.ApplicationRepository;
import com.enfint.dealservice.repository.ClientRepository;
import com.enfint.dealservice.repository.CreditRepository;
import com.enfint.dealservice.service.CalculationService;
import com.enfint.dealservice.utils.ApplicationStatusEnum;
import com.enfint.dealservice.utils.CreditStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalculationServiceImpl implements CalculationService {

    private final ApplicationRepository applicationRepository;
    private final CreditRepository creditRepository;
    private final ClientRepository clientRepository;
    private final ConveyorClient conveyorClient;
    private final ModelMapper mapper;

    @Override
    public void getApplicationById(ScoringDataDTO scoringData, Long applicationId) {

        log.info("**********completion of registration and full credit calculation**********");

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Could not find application with id: " + applicationId));

        Client client = application.getClient();
        LoanOfferDTO loanOffer = application.getAppliedOffer();

        scoringData = ScoringDataDTO
                .builder()
                .amount(loanOffer.getRequestedAmount())
                .gender(scoringData.getGender())
                .maritalStatus(scoringData.getMaritalStatus())
                .dependentAmount(scoringData.getDependentAmount())
                .employment(scoringData.getEmployment())
                .account(scoringData.getAccount())
                .term(loanOffer.getTerm())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .birthDate(client.getBirthDate())
                .passportSeries(client.getPassportSeries())
                .passportNumber(client.getPassportNumber())
                .isInsuranceEnabled(loanOffer.getIsInsuranceEnabled())
                .isSalaryClient(loanOffer.getIsSalaryClient())
                .passportIssueBranch(scoringData.getPassportIssueBranch())
                .passportIssueDate(scoringData.getPassportIssueDate())
                .build();

        ResponseEntity<CreditDTO> conveyorResponseEntity = conveyorClient.calculations(scoringData);
        CreditDTO creditDTO = conveyorResponseEntity.getBody();

        assert creditDTO != null;
        Credit creditEntity = Credit.
                 builder()
                .amount(creditDTO.getAmount())
                .application(application)
                .client(client)
                .isInsuranceEnabled(creditDTO.getIsInsuranceEnabled())
                .isSalaryClient(creditDTO.getIsSalaryClient())
                .monthlyPayment(creditDTO.getMonthlyPayment())
                .paymentSchedule(creditDTO.getPaymentSchedule())
                .psk(creditDTO.getPsk())
                .rate(creditDTO.getRate())
                .term(creditDTO.getTerm())
                .creditStatus(CreditStatusEnum.CALCULATED)
                .build();

        creditRepository.save(creditEntity);

        client.setGender(scoringData.getGender());
        client.setMaritalStatus(scoringData.getMaritalStatus());
        client.setDependentNumber(scoringData.getDependentAmount());
        client.setEmployment(scoringData.getEmployment());
        client.setAccount(scoringData.getAccount());
        client.setCredit(creditEntity);
        client.setPassportSeries(scoringData.getPassportSeries());
        client.setPassportNumber(scoringData.getPassportNumber());
        client.setIssueBranch(scoringData.getPassportIssueBranch());
        client.setIssueDate(scoringData.getPassportIssueDate());
        client.setSalary(scoringData.getEmployment().getSalary());
        client.setWorkExperienceCurrent(scoringData.getEmployment().getWorkExperienceCurrent());
        client.setWorkExperienceTotal(scoringData.getEmployment().getWorkExperienceTotal());
        client.setPosition(scoringData.getEmployment().getPosition());

        clientRepository.save(client);

        application.setStatus(ApplicationStatusEnum.APPROVED);
        applicationRepository.save(application);

    }
}
