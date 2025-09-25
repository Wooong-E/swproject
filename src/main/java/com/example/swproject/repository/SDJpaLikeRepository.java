package com.example.swproject.repository;

import com.example.swproject.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SDJpaLikeRepository extends JpaRepository<Like, Long> {

  public Optional<Like> findByUserIdAndPlaceId(Long userId, Long placeId);
}