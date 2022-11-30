package com.enfint.dealservice.service.impl;

import com.enfint.dealservice.dto.LoanApplicationRequestDTO;
import com.enfint.dealservice.dto.LoanOfferDTO;
import com.enfint.dealservice.entity.Application;
import com.enfint.dealservice.entity.Client;
import com.enfint.dealservice.feignclient.ConveyorClient;
import com.enfint.dealservice.repository.ApplicationRepository;
import com.enfint.dealservice.repository.ClientRepository;
import com.enfint.dealservice.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ClientRepository clientRepository;
    private final ConveyorClient conveyorClient;

    @Override
    public List<LoanOfferDTO> loanApplication(LoanApplicationRequestDTO loanApplicationRequest) {

        log.info("**********calculation of possible loan conditions**********");

        Client client = Client.builder()
                .lastName(loanApplicationRequest.getLastName())
                .firstName(loanApplicationRequest.getFirstName())
                .middleName(loanApplicationRequest.getMiddleName())
                .birthDate(loanApplicationRequest.getBirthDate())
                .email(loanApplicationRequest.getEmail())
                .passportNumber(loanApplicationRequest.getPassportNumber())
                .passportSeries(loanApplicationRequest.getPassportSeries())
                .build();

        Client saveClient = clientRepository.save(client);

        log.info("**********creating application**********");
        Application application = Application.builder()
                .client(saveClient)
                .creationDate(LocalDate.now())
                .build();

        Application saveApplication = applicationRepository.save(application);
        saveClient.setApplication(saveApplication);

        ResponseEntity<List<LoanOfferDTO>> conveyorResponseEntity = conveyorClient.loanOffers(loanApplicationRequest);

        List<LoanOfferDTO> loanOffers = conveyorResponseEntity.getBody();

        assert loanOffers != null;
        loanOffers.forEach(loanOffer -> loanOffer.setApplicationId(saveApplication.getId()));

        return loanOffers;

    }
}
