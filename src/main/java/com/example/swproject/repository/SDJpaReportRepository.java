package com.example.swproject.repository;

import com.example.swproject.domain.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SDJpaReportRepository extends JpaRepository<Report, Long> {
}