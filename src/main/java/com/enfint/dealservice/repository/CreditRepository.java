package com.enfint.dealservice.repository;

import com.enfint.dealservice.entity.Credit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepository extends JpaRepository<Credit, Long> {
}
