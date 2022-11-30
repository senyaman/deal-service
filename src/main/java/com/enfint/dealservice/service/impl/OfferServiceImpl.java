package com.enfint.dealservice.service.impl;

import com.enfint.dealservice.dto.ApplicationStatusHistoryDTO;
import com.enfint.dealservice.dto.LoanOfferDTO;
import com.enfint.dealservice.entity.Application;
import com.enfint.dealservice.exception.ApplicationNotFoundException;
import com.enfint.dealservice.repository.ApplicationRepository;
import com.enfint.dealservice.service.OfferService;
import com.enfint.dealservice.utils.ApplicationStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OfferServiceImpl implements OfferService {

    private final ApplicationRepository applicationRepository;

    @Override
    public void updateOffer(LoanOfferDTO loanOffer) {

        log.info("**********Selecting an offer**********");

        Application application;

        if(loanOffer.getApplicationId() != null) {
            application = applicationRepository.getReferenceById(loanOffer.getApplicationId());
        } else {
            throw new ApplicationNotFoundException("Could not find application");
        }

        application.setStatusHistory(List.of(
                ApplicationStatusHistoryDTO.builder()
                        .applicationStatus(ApplicationStatusEnum.APPROVED)
                        .build()
        ));

        application.setAppliedOffer(loanOffer);

        applicationRepository.save(application);

    }
}
