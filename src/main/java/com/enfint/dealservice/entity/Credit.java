package com.enfint.dealservice.entity;

import com.enfint.dealservice.dto.PaymentScheduleElement;
import com.enfint.dealservice.utils.CreditStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "credits")
public class Credit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;
    private Integer term;

    @Column(name = "monthly_payment")
    private BigDecimal monthlyPayment;

    private BigDecimal rate;
    private BigDecimal flc;

    @Column(name = "payment_schedule")
    private List<PaymentScheduleElement> paymentSchedule;

    @Column(name = "is_insurance_enabled")
    private Boolean isInsuranceEnabled;

    @Column(name = "is_salary_client")
    private Boolean isSalaryClient;

    @Column(name = "credit_status")
    private CreditStatusEnum creditStatus;
}
