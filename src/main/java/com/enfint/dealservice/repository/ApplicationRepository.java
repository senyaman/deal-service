package com.enfint.dealservice.repository;

import com.enfint.dealservice.entity.Application;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
