package com.enfint.dealservice.feignclient;

import com.enfint.dealservice.dto.CreditDTO;
import com.enfint.dealservice.dto.LoanApplicationRequestDTO;
import com.enfint.dealservice.dto.LoanOfferDTO;
import com.enfint.dealservice.dto.ScoringDataDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "conveyor-service", url = "http://localhost:8080/conveyor")
public interface ConveyorClient {

    @PostMapping("/offers")
    ResponseEntity<List<LoanOfferDTO>> loanOffers(@Valid @RequestBody LoanApplicationRequestDTO application);

    @PostMapping("/calculation")
    ResponseEntity<CreditDTO> calculations(@RequestBody ScoringDataDTO scoringDataDTO);
}
