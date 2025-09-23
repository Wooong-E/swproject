package com.example.swproject.repository;

import com.example.swproject.domain.Badge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SDJpaBadgeRepository extends JpaRepository<Badge, Long> {

}