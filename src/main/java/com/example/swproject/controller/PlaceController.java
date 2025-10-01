package com.example.swproject.controller;

import com.example.swproject.dto.AttractionDto;
import com.example.swproject.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// 장소 세부 카테고리를 보여주기 위한 controller.
@RestController
@RequestMapping("/api/places")
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    public ResponseEntity<List<AttractionDto>> getPlaces(@RequestParam(defaultValue = "nature") String category) {
        List<AttractionDto> places = placeService.getAttractionsByCategory(category);
        return ResponseEntity.ok(places);
    }
}
