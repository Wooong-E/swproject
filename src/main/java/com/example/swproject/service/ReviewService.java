package com.example.swproject.service;

import com.example.swproject.domain.Place;
import com.example.swproject.domain.Review;
import com.example.swproject.domain.ReviewsPost;
import com.example.swproject.domain.User;
import com.example.swproject.repository.PlaceRepository;
import com.example.swproject.repository.ReviewRepository;
import com.example.swproject.repository.ReviewsPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewsPostRepository reviewsPostRepository;
    private final PlaceRepository placeRepository;

    @Value("${upload.path}")
    private String uploadPath;

    public Review createReview(String title, String content, List<MultipartFile> images, User user, Long placeId) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid place Id:" + placeId));

        Review review = new Review();
        review.setTitle(title);
        review.setContent(content);
        review.setUser(user);
        review.setPlace(place);
        review.setGrade(0); // 별점 기능은 일단 0으로 고정
        Review savedReview = reviewRepository.save(review);

        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                if (image.isEmpty()) continue;

                try {
                    String originalFileName = image.getOriginalFilename();
                    String storedFileName = UUID.randomUUID().toString() + "_" + originalFileName;
                    Path destination = Paths.get(uploadPath, storedFileName);
                    Files.createDirectories(destination.getParent());
                    image.transferTo(destination);

                    ReviewsPost reviewsPost = new ReviewsPost();
                    reviewsPost.setReview(savedReview);
                    reviewsPost.setReviewsImageUrl(storedFileName);
                    reviewsPostRepository.save(reviewsPost);

                } catch (IOException e) {
                    throw new RuntimeException("Failed to store file.", e);
                }
            }
        }
        return savedReview;
    }

    @Transactional(readOnly = true)
    public List<Review> getReviewsByPlaceId(Long placeId) {
        return reviewRepository.findReviewByPlaceId(placeId);
    }

    @Transactional(readOnly = true)
    public Review findById(Long order) {
        Long reviewOrder = reviewRepository.findPlaceMaxOrder(order);
        return reviewRepository.(review)
                .orElseThrow(() -> new IllegalArgumentException("Invalid review Id:" + reviewId));
    }

    @Transactional(readOnly = true)
    public List<String> findImageUrlsByReviewId(Long reviewId) {
        return reviewsPostRepository.findByReviewId(reviewId).stream()
                .map(ReviewsPost::getReviewsImageUrl)
                .collect(Collectors.toList());
    }
}
