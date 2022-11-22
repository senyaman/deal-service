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
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalculationServiceImpl implements CalculationService {

    private final ApplicationRepository applicationRepository;
    private final CreditRepository creditRepository;
    private final ClientRepository clientRepository;
    private final ConveyorClient conveyorClient;
    private final ModelMapper mapper;

    @Override
    public void getApplicationById(ScoringDataDTO scoringData, Long applicationId) {

        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ApplicationNotFoundException("Could not find application with id: " + applicationId));

        Client client = application.getClient();
        LoanOfferDTO loanOffer = application.getAppliedOffer();

        scoringData = ScoringDataDTO
                .builder()
                .amount(loanOffer.getRequestedAmount())
                .term(loanOffer.getTerm())
                .firstName(client.getFirstName())
                .lastName(client.getLastName())
                .middleName(client.getMiddleName())
                .birthDate(client.getBirthDate())
                .passportSeries(client.getPassportSeries())
                .passportNumber(client.getPassportNumber())
                .isInsuranceEnabled(loanOffer.getIsInsuranceEnabled())
                .isSalaryClient(loanOffer.getIsSalaryClient())
                .passportIssueBranch(client.getIssueBranch())
                .passportIssueDate(client.getIssueDate())
                .build();

        ResponseEntity<CreditDTO> conveyorResponseEntity = conveyorClient.calculations(scoringData);
        CreditDTO creditDTO = conveyorResponseEntity.getBody();

        Credit creditEntity = mapper.map(creditDTO, Credit.class);
        creditEntity.setCreditStatus(CreditStatusEnum.CALCULATED);
        creditRepository.save(creditEntity);

        clientRepository.save(
                Client.builder()
                        .gender(scoringData.getGender())
                        .maritalStatus(scoringData.getMaritalStatus())
                        .dependentNumber(scoringData.getDependentAmount())
                        .employment(scoringData.getEmployment())
                        .account(scoringData.getAccount())
                        .credit(creditEntity)
                        .passportSeries(scoringData.getPassportSeries())
                        .passportNumber(scoringData.getPassportNumber())
                        .build()
        );

        application.setStatus(ApplicationStatusEnum.APPROVED);
        applicationRepository.save(application);

    }
}
