package com.example.swproject.controller;

import com.example.swproject.domain.Place;
import com.example.swproject.domain.User;
import com.example.swproject.service.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{placeId}/toggle")
    public ResponseEntity<?> toggleLike(@PathVariable Long placeId, @AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다.");
        }
        boolean isLiked = likeService.toggleLike(placeId, user);

        return ResponseEntity.ok(Map.of("liked", isLiked));
    }

    @GetMapping("/mine")
    public ResponseEntity<?> getMyLikes(@AuthenticationPrincipal User user) {
        if (user == null) {
            // 비로그인 사용자를 위해 빈 목록 반환
            return ResponseEntity.ok(Set.of());
        }
        Set<Long> likedPlaceIds = likeService.getLikedPlaceIdsByUser(user);
        return ResponseEntity.ok(likedPlaceIds);
    }

    @GetMapping("/categorized")
    public ResponseEntity<Map<String, List<Place>>> getLikedPlacesCategorized(@AuthenticationPrincipal User user) {
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        Map<String, List<Place>> categorizedLikes = likeService.getLikedPlacesCategorized(user);
        return ResponseEntity.ok(categorizedLikes);
    }
}
