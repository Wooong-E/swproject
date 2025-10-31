package com.example.swproject.repository;

import com.example.swproject.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SDJpaReviewRepository extends JpaRepository<Review, Long> {

  List<Review> findReviewByPlaceId(Long placeId);
}