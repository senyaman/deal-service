package com.enfint.dealservice.service;

import com.enfint.dealservice.dto.ScoringDataDTO;

public interface CalculationService {
    void getApplicationById(ScoringDataDTO scoringData, Long applicationId);
}
