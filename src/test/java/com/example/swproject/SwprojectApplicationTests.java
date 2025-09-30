package com.example.swproject;

import com.example.swproject.domain.Review;
import com.example.swproject.domain.ReviewsPost;
import com.example.swproject.repository.ReviewRepository;
import com.example.swproject.repository.ReviewsPostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class SwprojectApplicationTests {

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private ReviewsPostRepository reviewsPostRepository;

	@Test
	void contextLoads() {
	}

	@Test
	@Transactional
	void checkReviewsForPlaceId2_Temp() {
		System.out.println("--- [DEBUG] 데이터베이스에서 placeId = 2인 리뷰를 조회합니다 ---");
		List<Review> reviews = reviewRepository.findReviewByPlaceId(2L);

		if (reviews.isEmpty()) {
			System.out.println("[DEBUG] placeId = 2에 대한 조회된 리뷰가 없습니다.");
		} else {
			System.out.println("[DEBUG] " + reviews.size() + "개의 리뷰를 찾았습니다.");
			for (Review review : reviews) {
				System.out.println("----------------------------------------");
				System.out.println("[DEBUG] 리뷰 ID: " + review.getId());
				System.out.println("[DEBUG] 리뷰 제목: " + review.getTitle());

				// Fetch associated images for this review
				List<ReviewsPost> images = reviewsPostRepository.findByReviewsId(review.getId());
				if (images.isEmpty()) {
					System.out.println("[DEBUG] 연관된 이미지가 없습니다.");
				} else {
					System.out.println("[DEBUG] " + images.size() + "개의 연관된 이미지를 찾았습니다.");
					for (ReviewsPost image : images) {
						System.out.println("[DEBUG] 이미지 URL: " + image.getReviewsImageUrl());
					}
				}
			}
			System.out.println("----------------------------------------");
		}
		System.out.println("--- [DEBUG] 리뷰 조회 완료 ---");
	}
}
