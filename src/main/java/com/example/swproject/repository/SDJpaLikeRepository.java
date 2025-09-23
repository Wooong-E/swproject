package com.example.swproject.repository;

import com.example.swproject.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SDJpaLikeRepository extends JpaRepository<Like, Long> {
}