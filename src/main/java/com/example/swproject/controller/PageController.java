package com.example.swproject.controller;

import com.example.swproject.dto.UserLoginDTO;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class PageController {

    private void addLoginStatusToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isLoggedIn = authentication != null && authentication.isAuthenticated() && !(authentication instanceof AnonymousAuthenticationToken);
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

    @GetMapping("/")
    public String showIndexPage(Model model) {
        addLoginStatusToModel(model);
        return "index";
    }

    @GetMapping("/attractions")
    public String showAttractionsPage(Model model) {
        addLoginStatusToModel(model);
        return "attractions";
    }

    @GetMapping("/restaurants")
    public String showRestaurantsPage(Model model) {
        addLoginStatusToModel(model);
        return "restaurants";
    }

    @GetMapping("/cafes")
    public String showCafesPage(Model model) {
        addLoginStatusToModel(model);
        return "cafes";
    }

    @GetMapping("/attractions/{id}")
    public String showAttractionDetail(@PathVariable Long id, Model model) {
        addLoginStatusToModel(model);
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

    @GetMapping("/suggest")
    public String showSuggestPage(Model model) {
        addLoginStatusToModel(model);
        return "suggest";
    }




}
