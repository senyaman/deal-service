package com.enfint.dealservice.entity;

import com.enfint.dealservice.dto.ApplicationStatusHistoryDTO;
import com.enfint.dealservice.dto.LoanOfferDTO;
import com.enfint.dealservice.utils.ApplicationStatusEnum;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Table(name = "applications")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "credit_id", referencedColumnName = "id")
    private Credit credit;

    private ApplicationStatusEnum status;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @Column(name = "applied_offer", columnDefinition = "jsonb")
    @Type(type="jsonb")
    private LoanOfferDTO appliedOffer;

    @Column(name = "sign_date")
    private LocalDate signDate;

    @Column(name = "ses_code")
    private Integer sesCode;

    @Column(name = "status_history", columnDefinition = "jsonb")
    @Type(type="jsonb")
    private List<ApplicationStatusHistoryDTO> statusHistory;
}
