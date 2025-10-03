package com.example.swproject.controller;

import com.example.swproject.domain.Course;
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
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
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

    @PostMapping("/courses/course06")
    public String showCourse06Page(@RequestParam String startAddress,
                                   @RequestParam String fhash,
                                   @RequestParam String shash,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                   @RequestParam(required = false) List<Long> placeIds,
                                   Model model) {
        addLoginStatusToModel(model);
        model.addAttribute("currentPage", "courses");
        model.addAttribute("startAddress", startAddress);
        model.addAttribute("fhash", fhash);
        model.addAttribute("shash", shash);
        model.addAttribute("startDate", startDate.format(java.time.format.DateTimeFormatter.ISO_DATE));
        model.addAttribute("endDate", endDate.format(java.time.format.DateTimeFormatter.ISO_DATE));
        model.addAttribute("selectedPlaceIds", placeIds);

        List<Place> recommendedPlaces = courseService.recommendCourses(fhash, shash, placeIds);
        model.addAttribute("recommendedPlaces", recommendedPlaces);

        return "courses/course06";
    }

    @PostMapping("/courses/course07")
    public String showCourse07Page(@RequestParam String startAddress,
                                   @RequestParam String fhash,
                                   @RequestParam String shash,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                   @RequestParam(required = false) List<Long> placeIds,
                                   Model model) {
        addLoginStatusToModel(model);
        model.addAttribute("startAddress", startAddress);
        model.addAttribute("fhash", fhash);
        model.addAttribute("shash", shash);
        model.addAttribute("startDate", startDate.format(java.time.format.DateTimeFormatter.ISO_DATE));
        model.addAttribute("endDate", endDate.format(java.time.format.DateTimeFormatter.ISO_DATE));
        model.addAttribute("placeIds", placeIds);

        return "courses/course07";
    }

    @PostMapping("/courses/course08")
    public String showCourse08Page(@RequestParam String startAddress,
                                   @RequestParam String fhash,
                                   @RequestParam String shash,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                   @RequestParam(required = false) List<Long> placeIds,
                                   Model model) {
        addLoginStatusToModel(model);

        List<Place> selectedPlaces = new ArrayList<>();
        if (placeIds != null && !placeIds.isEmpty()) {
            selectedPlaces = placeIds.stream()
                    .map(id -> placeService.findById(id).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        model.addAttribute("startAddress", startAddress);
        model.addAttribute("fhash", fhash);
        model.addAttribute("shash", shash);
        model.addAttribute("displayStartDate", startDate.format(DateTimeFormatter.ofPattern("yy.MM.dd")));
        model.addAttribute("displayEndDate", endDate.format(DateTimeFormatter.ofPattern("yy.MM.dd")));
        model.addAttribute("rawStartDate", startDate);
        model.addAttribute("rawEndDate", endDate);
        model.addAttribute("selectedPlaces", selectedPlaces);

        return "courses/course08";
    }

    @PostMapping("/courses/09")
    public String previewCourseMap(@RequestParam String courseName, @RequestParam String startAddress,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                   @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                   @RequestParam List<Long> placeIds, Model model) {

        // Fetch Place objects to pass full details to the map view
        List<Place> selectedPlaces = placeIds.stream()
                .map(placeService::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());

        model.addAttribute("courseName", courseName);
        model.addAttribute("startAddress", startAddress);
        // Pass two date formats: one for display and one for the form value
        model.addAttribute("displayStartDate", startDate.format(DateTimeFormatter.ofPattern("yy.MM.dd")));
        model.addAttribute("displayEndDate", endDate.format(DateTimeFormatter.ofPattern("yy.MM.dd")));
        model.addAttribute("formStartDate", startDate.format(DateTimeFormatter.ISO_DATE)); // YYYY-MM-DD
        model.addAttribute("formEndDate", endDate.format(DateTimeFormatter.ISO_DATE)); // YYYY-MM-DD
        model.addAttribute("selectedPlaces", selectedPlaces); // Pass the list of Place objects

        addLoginStatusToModel(model);
        return "courses/course09"; // Corrected path
    }

    @PostMapping("/courses/save")
    @ResponseBody
    public org.springframework.http.ResponseEntity<String> saveCourse(@RequestParam String courseName, @RequestParam String startAddress,
                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                                                    @RequestParam List<Long> placeIds, @AuthenticationPrincipal User user) {
        try {
            courseService.saveCourse(courseName, startAddress, LocalDateTime.of(startDate, LocalTime.MIDNIGHT), LocalDateTime.of(endDate, LocalTime.MIDNIGHT), placeIds, user);
            return org.springframework.http.ResponseEntity.ok("코스가 성공적으로 저장되었습니다.");
        } catch (Exception e) {
            return org.springframework.http.ResponseEntity.status(org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR).body("코스 저장 중 오류가 발생했습니다.");
        }
    }

    @GetMapping("/my-courses")
    public String showMyCoursesPage(Model model, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "redirect:/users/login";
        }
        addLoginStatusToModel(model);

        List<List<Course>> allCourses = courseService.getAllCourses(user);
        model.addAttribute("allCourses", allCourses);

        return "my-courses";
    }

    @GetMapping("/my-courses/detail/{nth}")
    public String showMyCourseDetail(@PathVariable Long nth, Model model, @AuthenticationPrincipal User user) {
        if (user == null) {
            return "redirect:/users/login";
        }
        addLoginStatusToModel(model);

        List<Course> courseGroup = courseService.getCourse(user, nth);
        if (courseGroup == null || courseGroup.isEmpty()) {
            // Handle case where course is not found
            return "redirect:/my-courses";
        }

        // Extract data from the course group
        String courseName = courseGroup.get(0).getName();
        String startAddress = courseGroup.get(0).getStartaddress();
        LocalDate startDate = courseGroup.get(0).getStartdate().toLocalDate();
        LocalDate endDate = courseGroup.get(0).getEnddate().toLocalDate();
        // Convert Place entities to PlaceDto to avoid serialization issues with Hibernate proxies
        List<com.example.swproject.dto.PlaceDto> selectedPlacesDto = courseGroup.stream()
                .map(Course::getPlace)
                .map(place -> new com.example.swproject.dto.PlaceDto(place.getId(), place.getName(), place.getAddress(), place.getCategory()))
                .collect(Collectors.toList());

        // Add attributes to the model for the view
        model.addAttribute("courseName", courseName);
        model.addAttribute("startAddress", startAddress);
        model.addAttribute("displayStartDate", startDate.format(DateTimeFormatter.ofPattern("yy.MM.dd")));
        model.addAttribute("displayEndDate", endDate.format(DateTimeFormatter.ofPattern("yy.MM.dd")));
        model.addAttribute("selectedPlaces", selectedPlacesDto); // Pass DTO list
        model.addAttribute("isMyCourseView", true); // Flag to hide the save button

        return "courses/course09";
    }

    @PostMapping("/my-courses/delete")
    public String deleteCourse(@RequestParam Long nth, @AuthenticationPrincipal User user) {
        if (user != null) {
            courseService.deleteCourse(user, nth);
        }
        return "redirect:/my-courses";
    }
}
