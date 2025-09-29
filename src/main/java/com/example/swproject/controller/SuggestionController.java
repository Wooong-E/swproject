package com.example.swproject.controller;

import com.example.swproject.domain.User;
import com.example.swproject.service.SuggestionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/suggest")
public class SuggestionController {

    private final SuggestionService suggestionService;

    public SuggestionController(SuggestionService suggestionService) {
        this.suggestionService = suggestionService;
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<String> submitSuggestion(@RequestParam("placeName") String placeName,
                                                   @RequestParam("address") String address,
                                                   @RequestParam("placeType") String placeType,
                                                   @RequestParam(value = "images", required = false) List<MultipartFile> images,
                                                   @AuthenticationPrincipal User user) {
        // 1. 비로그인 사용자 차단
        if (user == null) {
            return new ResponseEntity<>("장소 제보를 하려면 로그인이 필요합니다.", HttpStatus.UNAUTHORIZED);
        }

        try {
            // 2. 서비스 호출
            suggestionService.sendSuggestion(placeName, address, placeType, images, user);
            // 3. 성공 응답 반환
            return new ResponseEntity<>("장소가 성공적으로 제보되었습니다.", HttpStatus.OK);
        } catch (Exception e) {
            // 4. 실패 응답 반환
            e.printStackTrace(); // 서버 로그에 에러 기록
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
