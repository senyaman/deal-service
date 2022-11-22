package com.enfint.dealservice.service;

import com.enfint.dealservice.dto.LoanApplicationRequestDTO;
import com.enfint.dealservice.dto.LoanOfferDTO;

import java.util.List;

public interface ApplicationService {
    List<LoanOfferDTO> loanApplication(LoanApplicationRequestDTO loanApplicationRequest);
}
