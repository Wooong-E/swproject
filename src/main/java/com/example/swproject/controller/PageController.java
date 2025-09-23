package com.example.SWFRONT;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import com.example.SWFRONT.MenuDto;
import com.example.SWFRONT.RestaurantDto;
import com.example.SWFRONT.CafeDto;

@Controller
public class PageController {

    @GetMapping("/attractions")
    public String attractionsPage(){
        return "attractions";
    }

    @GetMapping("/attractions/seoul-sky")
    public String seoulSkyDetailPage(){
        return "seoul-sky-detail";
    }

    @GetMapping("/attractions/lotte-world")
    public String lotteWorldDetailPage(){
        return "lotte-world-detail";
    }

    @GetMapping("/attractions/gyeongbokgung")
    public String gyeongbokgungDetailPage(){
        return "gyeongbokgung-detail";
    }

    @GetMapping("/attractions/n-seoul-tower")
    public String nSeoulTowerDetailPage(){
        return "n-seoul-tower-detail";
    }

    @GetMapping("/cafe-detail")
    public String cafeDetailPage(){
        return "cafe-detail";
    }

    @GetMapping("/restaurant-detail")
    public String restaurantDetailPage(){
        return "restaurant-detail";
    }

    @GetMapping("/report")
    public String showReportPage() {
        return "report";
    }

    @GetMapping("/suggest")
    public String showSuggestPage() {
        return "suggest";
    }
}
