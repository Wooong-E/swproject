package com.example.swproject.controller;

import com.example.swproject.domain.User;
import com.example.swproject.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate; // 이 import가 있는지 확인
import java.time.LocalDateTime;
import java.time.LocalTime; // 이 import가 있는지 확인
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    private void addLoginStatusToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() &&
                !(authentication instanceof AnonymousAuthenticationToken);
        model.addAttribute("isLoggedIn", isLoggedIn);
        if (isLoggedIn) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof com.example.swproject.domain.User) {
                model.addAttribute("userName", ((com.example.swproject.domain.User) principal).getName());
            } else {
                model.addAttribute("userName", authentication.getName()); // fallback
            }
        }
    }

    @GetMapping("/courses/course00")
    public String showCourse00Page(Model model) {
        addLoginStatusToModel(model);
        model.addAttribute("currentPage", "courses");
        return "courses/course00";
    }

    @GetMapping("/courses/course01")
    public String showCourse01Page(Model model) {
        addLoginStatusToModel(model);
        model.addAttribute("currentPage", "courses");
        return "courses/course01";
    }

    @GetMapping("/courses/course02")
    public String showCourse02Page(Model model) {
        addLoginStatusToModel(model);
        model.addAttribute("currentPage", "courses");
        return "courses/course02";
    }

    @GetMapping("/courses/course03")
    public String showCourse03Page(Model model) {
        addLoginStatusToModel(model);
        model.addAttribute("currentPage", "courses");
        return "courses/course03";
    }

    @GetMapping("/courses/course04")
    public String showCourse04Page(Model model) {
        addLoginStatusToModel(model);
        model.addAttribute("currentPage", "courses");
        return "courses/course04";
    }

    @PostMapping("/courses/recommend")
    public String getCourseRecommendations(@RequestParam String fhash, @RequestParam String shash,
                                           @RequestParam String startAddress, @RequestParam String
                                                   startDate,
                                           @RequestParam String endDate, Model model) {
        List<com.example.swproject.domain.Place> recommendedPlaces = courseService.recommendCourses(fhash,
                shash);
        model.addAttribute("recommendedPlaces", recommendedPlaces);
        model.addAttribute("startAddress", startAddress);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        addLoginStatusToModel(model);
        return "course-recommend";
    }

    @PostMapping("/courses/save")
    public String saveCourse(@RequestParam String courseName, @RequestParam String startAddress,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate
                                     startDate,
                             @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate
                                     endDate,
                             @RequestParam List<Long> placeIds, @AuthenticationPrincipal User user) {

        courseService.saveCourse(courseName, startAddress, LocalDateTime.of(startDate,
                LocalTime.MIDNIGHT), LocalDateTime.of(endDate, LocalTime.MIDNIGHT), placeIds, user);
        return "redirect:/";
    }

    @PostMapping("/courses/preview-map")
    public String previewCourseMap(@RequestParam String courseName, @RequestParam String startAddress,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                   @RequestParam List<Long> placeIds, Model model) {

        model.addAttribute("courseName", courseName);
        model.addAttribute("startAddress", startAddress);
        model.addAttribute("startDate", startDate.format(java.time.format.DateTimeFormatter.ISO_DATE));
        model.addAttribute("endDate", endDate.format(java.time.format.DateTimeFormatter.ISO_DATE));
        model.addAttribute("placeIds", placeIds);

        addLoginStatusToModel(model);
        return "course-map-preview";
    }
}
