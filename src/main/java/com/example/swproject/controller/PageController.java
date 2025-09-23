package com.example.swproject.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class PageController {

    @GetMapping("/attractions")
    public String showAttractionsPage() {
        return "attractions";
    }

    @GetMapping("/restaurants")
    public String showRestaurantsPage() {
        return "restaurants";
    }

    @GetMapping("/cafes")
    public String showCafesPage() {
        return "cafes";
    }

    @GetMapping("/attractions/{id}")
    public String showAttractionDetail(@PathVariable Long id, Model model) {
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
    public String showSuggestPage() {
        return "suggest";
    }
}
