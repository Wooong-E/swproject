package com.example.swproject.repository;

import com.example.swproject.domain.Like;
import com.example.swproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SDJpaLikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserIdAndPlaceId(Long userId, Long placeId);
    long countByUser(User user);
}
