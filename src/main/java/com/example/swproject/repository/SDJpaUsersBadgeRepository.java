package com.example.swproject.repository;

import com.example.swproject.domain.UsersBadge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SDJpaUsersBadgeRepository extends JpaRepository<UsersBadge, Long> {
}