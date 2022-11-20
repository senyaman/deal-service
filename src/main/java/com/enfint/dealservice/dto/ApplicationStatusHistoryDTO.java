package com.enfint.dealservice.dto;

import com.enfint.dealservice.utils.ApplicationStatusEnum;
import com.enfint.dealservice.utils.ChangeTypeEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApplicationStatusHistoryDTO {

    private ApplicationStatusEnum applicationStatus;
    private LocalDateTime time;
    private ChangeTypeEnum changeType;
}
