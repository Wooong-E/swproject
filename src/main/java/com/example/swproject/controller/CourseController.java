package com.example.swproject.controller;

import com.example.swproject.domain.User;
import com.example.swproject.service.CourseService;
import com.example.swproject.service.LikeService; // Import LikeService
import com.example.swproject.service.PlaceService;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map; // Import Map
import com.example.swproject.domain.Place; // Import Place

@Controller
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final LikeService likeService; // Inject LikeService
    private final PlaceService placeService; // Inject PlaceService

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

    @PostMapping("/courses/course02")
    public String showCourse02Page(@RequestParam String startAddress, Model model) {
        addLoginStatusToModel(model);
        model.addAttribute("currentPage", "courses");
        model.addAttribute("startAddress", startAddress);
        return "courses/course02";
    }

    @PostMapping("/courses/course03")
    public String showCourse03Page(@RequestParam String startAddress, @RequestParam String fhash, Model model) {
        addLoginStatusToModel(model);
        model.addAttribute("currentPage", "courses");
        model.addAttribute("startAddress", startAddress);
        model.addAttribute("fhash", fhash);
        return "courses/course03";
    }

    @PostMapping("/courses/course04")
    public String showCourse04Page(@RequestParam String startAddress, @RequestParam String fhash, @RequestParam String shash, Model model) {
        addLoginStatusToModel(model);
        model.addAttribute("currentPage", "courses");
        model.addAttribute("startAddress", startAddress);
        model.addAttribute("fhash", fhash);
        model.addAttribute("shash", shash);
        return "courses/course04";
    }

    @PostMapping("/courses/course05")
    public String showCourse05Page(@RequestParam String startAddress,
                                   @RequestParam String fhash,
                                   @RequestParam String shash,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                   Model model,
                                   @AuthenticationPrincipal User user) {
        addLoginStatusToModel(model);
        model.addAttribute("currentPage", "courses");
        model.addAttribute("startAddress", startAddress);
        model.addAttribute("fhash", fhash);
        model.addAttribute("shash", shash);
        model.addAttribute("startDate", startDate.format(java.time.format.DateTimeFormatter.ISO_DATE));
        model.addAttribute("endDate", endDate.format(java.time.format.DateTimeFormatter.ISO_DATE));

        if (user != null) {
            Map<String, List<Place>> categorizedLikedPlaces = likeService.getLikedPlacesCategorized(user);
            model.addAttribute("categorizedLikedPlaces", categorizedLikedPlaces); // Pass categorized list to model
        } else {
            model.addAttribute("categorizedLikedPlaces", Map.of()); // Empty map for non-logged-in users
        }

        return "courses/course05";
    }

    @PostMapping("/courses/recommend")
    public String getCourseRecommendations(@RequestParam String fhash, @RequestParam String shash,
                                           @RequestParam String startAddress, 
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                           @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, Model model) {
        List<com.example.swproject.domain.Place> recommendedPlaces = courseService.recommendCourses(fhash,
                shash);
        
        model.addAttribute("recommendedPlaces", recommendedPlaces);
        model.addAttribute("startAddress", startAddress);
        model.addAttribute("startDate", startDate.format(java.time.format.DateTimeFormatter.ISO_DATE)); // Pass as String for frontend
        model.addAttribute("endDate", endDate.format(java.time.format.DateTimeFormatter.ISO_DATE));     // Pass as String for frontend
        addLoginStatusToModel(model);
        return "course-recommend";    }

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
        model.addAttribute("startDate", startDate.format(java.time.format.DateTimeFormatter.ISO_DATE)); // Pass as String for frontend
        model.addAttribute("endDate", endDate.format(java.time.format.DateTimeFormatter.ISO_DATE));     // Pass as String for frontend
        model.addAttribute("placeIds", placeIds);
        // You might want to fetch Place objects here to pass more details to the map view
        // For now, just passing IDs
        addLoginStatusToModel(model);
        return "course-map-preview";
    }
}
