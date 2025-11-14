package com.example.swproject.controller;

import com.example.swproject.domain.Place;
import com.example.swproject.domain.User;
import com.example.swproject.dto.UserStatsDto;
import com.example.swproject.service.CourseService;
import com.example.swproject.service.PlaceService;
import com.example.swproject.service.UserStatsService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/monthly-magazine")
public class MagazineController {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class MagazineItemDto {
        private Long id;
        private String imageUrl;
        private String title;
        private String snippet;
    }

    private final PlaceService placeService;
    private final CourseService courseService;
    private final UserStatsService userStatsService;

    public MagazineController(PlaceService placeService, CourseService courseService, UserStatsService userStatsService) {
        this.placeService = placeService;
        this.courseService = courseService;
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

    @GetMapping("")
    public String showMonthlyMagazinePage(@RequestParam(value = "page", defaultValue = "1") int page, Model model) {
        addLoginStatusToModel(model);
        model.addAttribute("currentPage", "monthly-magazine");

        // Add current date components to the model
        LocalDate today = LocalDate.now();
        model.addAttribute("currentYear", today.getYear());
        model.addAttribute("currentMonth", today.getMonth().toString().substring(0, 3)); // e.g., OCT
        model.addAttribute("currentDayOfWeek", today.getDayOfWeek().toString().substring(0, 3)); // e.g., MON
        model.addAttribute("currentDayOfMonth", today.getDayOfMonth());

        final int TOTAL_ITEMS = 30;
        final int ITEMS_PER_PAGE = 3;

        List<MagazineItemDto> allItems = IntStream.rangeClosed(1, TOTAL_ITEMS)
                .mapToObj(i -> {
                    if (i == 1) {
                        return new MagazineItemDto(
                                1L,
                                "/images/magazine/main/starmain.png",
                                "길 위에서 01. 경산 스타필드",
                                "책, 커피, 여가를 한 번에! 경산 스타필드에서 보낸 하루"
                        );
                    } else if (i == 2) {
                        return new MagazineItemDto(
                                2L,
                                "/images/magazine/main/bmain.png",
                                "길 위에서 02. 경산 별찌야시장",
                                "별빛 아래, 한국 문화를 경험하다! 개발자 갱의 야시장 탐방기"
                        );
                    } else {
                        return new MagazineItemDto(
                                (long) i,
                                "/images/monthly-magazine" + i + ".png",
                                "월간매거진_제목" + i,
                                "월간매거진_본문" + i
                        );
                    }
                })
                .collect(Collectors.toList());

        int totalPages = (int) Math.ceil((double) TOTAL_ITEMS / ITEMS_PER_PAGE);
        int currentPage = Math.max(1, Math.min(page, totalPages));

        int start = (currentPage - 1) * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, TOTAL_ITEMS);
        List<MagazineItemDto> pageItems = allItems.subList(start, end);

        model.addAttribute("magazineItems", pageItems);
        model.addAttribute("pageNum", currentPage);
        model.addAttribute("totalPages", totalPages);

        return "monthly-magazine";
    }

    @GetMapping("/{id}")
    public String showMonthlyMagazineDetailPage(@PathVariable Long id, Model model, @AuthenticationPrincipal User user) {
        if (id == 1) {
            addLoginStatusToModel(model);
            model.addAttribute("currentPage", "monthly-magazine"); // To highlight the monthly-magazine link in header

            List<Place> recommendedPlaces = new ArrayList<>();
            placeService.findById(3L).ifPresent(recommendedPlaces::add);

            model.addAttribute("recommendedCourseName", "개발자 갱의 코스");
            model.addAttribute("recommendedCourseHashtags", List.of("#힐링여행", "#나혼자"));
            model.addAttribute("recommendedCourseStartDate", LocalDate.of(2025, 12, 3));
            model.addAttribute("recommendedCourseEndDate", LocalDate.of(2025, 12, 4));
            model.addAttribute("recommendedCoursePlaces", recommendedPlaces);
            model.addAttribute("recommendedCourseStartAddress", "경북 경산시 대학로 지하 270");

            Long recommendedCourseNth = null;
            if (user != null) {
                recommendedCourseNth = courseService.findNthByCourseName(user, "개발자 갱의 코스");
            }

            model.addAttribute("isRecommendedCourseSaved", recommendedCourseNth != null);
            model.addAttribute("recommendedCourseNth", recommendedCourseNth);

            return "monthly-magazine-detail";
        } else if (id == 2) {
            addLoginStatusToModel(model);
            model.addAttribute("currentPage", "monthly-magazine");

            List<Place> recommendedPlaces = new ArrayList<>();
            placeService.findById(3L).ifPresent(recommendedPlaces::add); // 임시로 '경산 스타필드' 사용

            model.addAttribute("recommendedCourseName", "개발자 만두의 코스");
            model.addAttribute("recommendedCourseHashtags", List.of("#야시장", "#데이트"));
            model.addAttribute("recommendedCourseStartDate", LocalDate.of(2025, 11, 30));
            model.addAttribute("recommendedCourseEndDate", LocalDate.of(2025, 12, 8));
            model.addAttribute("recommendedCoursePlaces", recommendedPlaces);
            model.addAttribute("recommendedCourseStartAddress", "경북 경산시 대학로 지하 270");
            model.addAttribute("recommendedCourseEndAddress", "경상북도 경산시 경안로31길 19");

            Long recommendedCourseNth = null;
            if (user != null) {
                recommendedCourseNth = courseService.findNthByCourseName(user, "개발자 만두의 코스");
            }

            model.addAttribute("isRecommendedCourseSaved", recommendedCourseNth != null);
            model.addAttribute("recommendedCourseNth", recommendedCourseNth);

            return "monthly-magazine-detail-2";
        } else {
            return "redirect:/monthly-magazine";
        }
    }
}
