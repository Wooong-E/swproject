package com.example.swproject.controller;

import com.example.swproject.domain.Place;
import com.example.swproject.domain.User;
import com.example.swproject.repository.PlaceRepository;
import com.example.swproject.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final PlaceRepository placeRepository;

    @PostMapping("/places/{placeId}/reviews")
    public String createReview(@PathVariable Long placeId,
                               @RequestParam String title,
                               @RequestParam String content,
                               @RequestParam(value = "images", required = false) List<MultipartFile> images,
                               @AuthenticationPrincipal User user) {

        if (user == null) {
            return "redirect:/users/login";
        }

        reviewService.createReview(title, content, images, user, placeId);

        // 리뷰 작성 후, 메인 홈으로 리다이렉트
        return "redirect:/";
    }
}
