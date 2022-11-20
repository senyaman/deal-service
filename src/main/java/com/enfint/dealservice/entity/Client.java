package com.enfint.dealservice.entity;

import com.enfint.dealservice.dto.EmploymentDTO;
import com.enfint.dealservice.utils.GenderEnum;
import com.enfint.dealservice.utils.MaritalStatusEnum;
import com.enfint.dealservice.utils.PositionEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    private String email;

    private GenderEnum gender;

    @Column(name = "marital_status")
    private MaritalStatusEnum maritalStatus;

    @Column(name = "dependent_number")
    private Integer dependentNumber;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "issue_brand")
    private String issueBranch;

    private EmploymentDTO employment;

    private String employer;

    private BigDecimal salary;

    private PositionEnum position;

    @Column(name = "work_experience_total")
    private Integer workExperienceTotal;

    @Column(name = "work_experience_current")
    private Integer workExperienceCurrent;
    private String account;
}
