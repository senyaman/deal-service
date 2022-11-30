package com.enfint.dealservice.controller;

import com.enfint.dealservice.dto.LoanApplicationRequestDTO;
import com.enfint.dealservice.dto.LoanOfferDTO;
import com.enfint.dealservice.dto.ScoringDataDTO;
import com.enfint.dealservice.service.ApplicationService;
import com.enfint.dealservice.service.CalculationService;
import com.enfint.dealservice.service.OfferService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Api(value = "REST APIs for the deal-resources")
@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DealController {

    private final ApplicationService applicationService;
    private final OfferService offerService;
    private final CalculationService calculationService;

    @ApiOperation(value = "Loan application")
    @PostMapping("/application")
    public ResponseEntity<List<LoanOfferDTO>> loanApplication(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequest) {
        List<LoanOfferDTO> loanOffers = applicationService.loanApplication(loanApplicationRequest);
        return ResponseEntity.ok(loanOffers);
    }

    @ApiOperation(value = "Update loan offer details")
    @PutMapping("/offer")
    public void updateOffer(@RequestBody LoanOfferDTO loanOffer) {
        offerService.updateOffer(loanOffer);
    }

    @ApiOperation(value = "Get application by ID. Completes client registration.")
    @PutMapping("/calculate/{applicationId}")
    public void getApplicationById(@RequestBody ScoringDataDTO scoringData, @PathVariable Long applicationId) {
        calculationService.getApplicationById(scoringData, applicationId);
    }
}
