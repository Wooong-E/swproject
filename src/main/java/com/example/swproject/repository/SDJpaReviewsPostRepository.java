package com.example.swproject.repository;

import com.example.swproject.domain.ReviewsPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SDJpaReviewsPostRepository extends JpaRepository<ReviewsPost, Long> {
}