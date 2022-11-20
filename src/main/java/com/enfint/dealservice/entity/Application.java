package com.enfint.dealservice.entity;

import com.enfint.dealservice.dto.ApplicationStatusHistoryDTO;
import com.enfint.dealservice.dto.LoanOfferDTO;
import com.enfint.dealservice.utils.ApplicationStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Client clientID;

    private Credit creditID;

    private ApplicationStatusEnum status;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "applied_offer")
    private LoanOfferDTO appliedOffer;

    @Column(name = "sign_date")
    private LocalDate signDate;

    @Column(name = "ses_code")
    private Integer sesCode;

    @Column(name = "status_history")
    private List<ApplicationStatusHistoryDTO> statusHistory;
}
