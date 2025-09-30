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

import com.example.swproject.service.PlaceService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final PlaceService placeService;

    // 리뷰를 작성하기 위한 controller
    @GetMapping("/places/{placeId}/reviews/new")
    public String showReviewWritePage(@PathVariable Long placeId, Model model) {
        Optional<Place> placeOptional = placeService.findById(placeId);
        if (placeOptional.isPresent()) {
            String placeName = placeOptional.get().getName();
            if (placeName == null || placeName.trim().isEmpty()) {
                placeName = "알 수 없는 장소"; // Default name if empty or null
            }
            model.addAttribute("placeName", placeName);
            model.addAttribute("placeId", placeId);
            return "review-write";
        } else {
            // Handle case where place is not found
            return "redirect:/error"; // Or some error page
        }
    }

    // 작성한 리뷰를 전송하기 위한 controller
    @PostMapping("/places/{placeId}/reviews")
    public String createReview(@PathVariable Long placeId,
                               @RequestParam String title,
                               @RequestParam String content,
                               @RequestParam Integer grade,
                               @RequestParam String fhash,
                               @RequestParam String shash,
                               @RequestParam(value = "images", required = false) List<MultipartFile> images,
                               @AuthenticationPrincipal User user) {

        // todo: 리뷰란을 들어갈 때 이미 로그인했는 지 검증 과정을 거치기에, 불필요한 코드로 추측.
        if (user == null) {
            return "redirect:/users/login";
        }

        reviewService.createReview(title, content, grade, fhash, shash, images, user, placeId);

        // 리뷰 작성 후, 메인 홈으로 리다이렉트
        return "redirect:/";
    }
}
