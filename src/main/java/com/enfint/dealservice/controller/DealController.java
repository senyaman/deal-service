package com.enfint.dealservice.controller;

import com.enfint.dealservice.dto.LoanApplicationRequestDTO;
import com.enfint.dealservice.dto.LoanOfferDTO;
import com.enfint.dealservice.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DealController {

    private final ApplicationService applicationService;

    @PostMapping("/application")
    public ResponseEntity<List<LoanOfferDTO>> loanApplication(@Valid @RequestBody LoanApplicationRequestDTO loanApplicationRequest) {
        List<LoanOfferDTO> loanOffers = applicationService.loanApplication(loanApplicationRequest);
        return ResponseEntity.ok(loanOffers);
    }
}
