package com.example.swproject.controller;

import com.example.swproject.domain.Place;
import com.example.swproject.domain.User;
import com.example.swproject.dto.*;
import com.example.swproject.service.UserStatsService;
import com.example.swproject.domain.Place;
import com.example.swproject.dto.ReviewSummaryDto;
import com.example.swproject.service.CourseService;
import com.example.swproject.service.PlaceService;
import com.example.swproject.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.swproject.domain.Review;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class PageController {

    private final ReviewService reviewService;
    private final PlaceService placeService;

    private final UserStatsService userStatsService;

    public PageController(ReviewService reviewService, PlaceService placeService, UserStatsService userStatsService) {
        this.reviewService = reviewService;
        this.placeService = placeService;
        this.userStatsService = userStatsService;
    }

    private void addLoginStatusToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);
        if (isLoggedIn) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof com.example.swproject.domain.User) {
                User user = (com.example.swproject.domain.User) principal;
                model.addAttribute("userName", user.getName());
                UserStatsDto userStats = userStatsService.getUserStats(user);
                model.addAttribute("userStats", userStats);
            } else {
                model.addAttribute("userName", authentication.getName()); // fallback
                model.addAttribute("userStats", new UserStatsDto(0, 0, 0)); // fallback
            }
        }
    }

    @GetMapping("/")
    public String showIndexPage(Model model) {
        addLoginStatusToModel(model);
        return "index";
    }

    @GetMapping("/attractions")
    public String showAttractionsPage(Model model) {
        addLoginStatusToModel(model);
        model.addAttribute("currentPage", "attractions");
        return "attractions";
    }

    @GetMapping("/restaurants")
    public String showRestaurantsPage(Model model) {
        addLoginStatusToModel(model);
        model.addAttribute("currentPage", "restaurants");
        return "restaurants";
    }

    @GetMapping("/cafes")
    public String showCafesPage(Model model) {
        addLoginStatusToModel(model);
        model.addAttribute("currentPage", "cafes");
        return "cafes";
    }

    @GetMapping("/attractions/{id}")
    public String showAttractionDetail(@PathVariable Long id, Model model) {
        addLoginStatusToModel(model);
        List<ReviewSummaryDto> reviewSummaries = reviewService.getReviewSummariesByPlaceId(id);
        model.addAttribute("reviews", reviewSummaries);
        //별점 평균을 구하는 코드
        Double averageGrade = reviewService.getAverageGrade(id);
        model.addAttribute("averageGrade", averageGrade != null ? averageGrade : 0.0);

        model.addAttribute("id", id); // For template selection
        model.addAttribute("placeId", id); // For correct linking

        if (id == 1L) {
            return "attraction-detail-1";
        } else if (id == 2L) {
            return "attraction-detail-2";
        } else if (id == 3L) {
            return "attraction-detail-3";
        } else if (id == 4L) {
            return "attraction-detail-4";
        } else if (id == 5L) {
            return "attraction-detail-5";
        } else if (id == 6L) {
            return "attraction-detail-6";
        }
        return "attraction-detail-1"; // Default or error page
    }

    @GetMapping("/restaurants/{id}")
    public String showRestaurantDetail(@PathVariable Long id, Model model) {
        addLoginStatusToModel(model);
        Long placeId = id + 6;
        List<ReviewSummaryDto> reviewSummaries = reviewService.getReviewSummariesByPlaceId(placeId);
        model.addAttribute("reviews", reviewSummaries);

        Double averageGrade = reviewService.getAverageGrade(placeId);
        model.addAttribute("averageGrade", averageGrade != null ? averageGrade : 0.0);

        model.addAttribute("id", id);
        model.addAttribute("placeId", placeId);

        if (id == 1L) {
            return "restaurant-detail-1";
        } else if (id == 2L) {
            return "restaurant-detail-2";
        } else if (id == 3L) {
            return "restaurant-detail-3";
        } else if (id == 4L) {
            return "restaurant-detail-4";
        } else if (id == 5L) {
            return "restaurant-detail-5";
        } else if (id == 6L) {
            return "restaurant-detail-6";
        }
        return "restaurant-detail-1"; // Default or error page
    }

    @GetMapping("/cafes/{id}")
    public String showCafeDetail(@PathVariable Long id, Model model) {
        addLoginStatusToModel(model);
        Long placeId = id + 12;
        List<ReviewSummaryDto> reviewSummaries = reviewService.getReviewSummariesByPlaceId(placeId);
        model.addAttribute("reviews", reviewSummaries);

        Double averageGrade = reviewService.getAverageGrade(placeId);
        model.addAttribute("averageGrade", averageGrade != null ? averageGrade : 0.0);

        model.addAttribute("id", id);
        model.addAttribute("placeId", placeId);

        if (id == 1L) {
            return "cafe-detail-1";
        } else if (id == 2L) {
            return "cafe-detail-2";
        } else if (id == 3L) {
            return "cafe-detail-3";
        } else if (id == 4L) {
            return "cafe-detail-4";
        } else if (id == 5L) {
            return "cafe-detail-5";
        } else if (id == 6L) {
            return "cafe-detail-6";
        }
        return "cafe-detail-1"; // Default or error page
    }


    @GetMapping("/api/places/summary")
    @ResponseBody
    public ResponseEntity<List<PlaceSummaryDto>> getPlacesSummary() {
        List<Place> allPlaces = placeService.findAllPlaces();
        List<PlaceSummaryDto> summaries = allPlaces.stream()
                .map(place -> {
                    Double averageGrade = reviewService.getAverageGrade(place.getId());
                    return new PlaceSummaryDto(
                            place.getId(),
                            place.getName(),
                            place.getCategory(),
                            averageGrade != null ? averageGrade : 0.0
                    );
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(summaries);
    }

    //todo:이쪽 서상범 추가 리뷰 상세보기 위해서
    @GetMapping("/reviews/{placeId}/{orderId}")
    public String showReviewDetail(@PathVariable Long placeId,  @PathVariable Long orderId, Model model) {
        addLoginStatusToModel(model);

        Review review = reviewService.findById(orderId,placeId);
        List<String> imageUrls = reviewService.findImageUrlsByReviewId(orderId,placeId).stream()
                .map(url -> "/uploads/" + url)
                .collect(Collectors.toList());

        model.addAttribute("review", review);
        model.addAttribute("imageUrls", imageUrls);

        return "review-detail";
    }

    @GetMapping("/suggest")
    public String showSuggestPage(Model model){
        addLoginStatusToModel(model);
        return "suggest";
    }

    @GetMapping("/my-place")
    public String showMyPlacePage(Model model){
        addLoginStatusToModel(model);
        return "my-place";
    }

}
