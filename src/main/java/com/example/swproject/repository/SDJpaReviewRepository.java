package com.example.swproject.repository;

import com.example.swproject.domain.Review;
import com.example.swproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SDJpaReviewRepository extends JpaRepository<Review, Long> {
    long countByUser(User user);
}
