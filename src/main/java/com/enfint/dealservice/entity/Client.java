package com.enfint.dealservice.entity;

import com.enfint.dealservice.dto.EmploymentDTO;
import com.enfint.dealservice.utils.GenderEnum;
import com.enfint.dealservice.utils.MaritalStatusEnum;
import com.enfint.dealservice.utils.PositionEnum;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

@Entity
@Table(name = "clients")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
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

    @Enumerated(EnumType.STRING)
    private GenderEnum gender;

    @Column(name = "marital_status")
    @Enumerated(EnumType.STRING)
    private MaritalStatusEnum maritalStatus;

    @Column(name = "dependent_number")
    private Integer dependentNumber;

    @Column(name = "passport_series")
    private String passportSeries;

    @Column(name = "passport_number")
    private String passportNumber;

    @Column(name = "issue_date")
    private LocalDate issueDate;

    @Column(name = "issue_branch")
    private String issueBranch;

    @Type(type="jsonb")
    @Column(columnDefinition = "jsonb")
    private EmploymentDTO employment;

    private String employer;

    private BigDecimal salary;

    @Enumerated(EnumType.STRING)
    private PositionEnum position;

    @Column(name = "work_experience_total")
    private Integer workExperienceTotal;

    @Column(name = "work_experience_current")
    private Integer workExperienceCurrent;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "application_id", referencedColumnName = "id")
    private Application application;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "credit_id", referencedColumnName = "id")
    private Credit credit;

    private String account;
}
