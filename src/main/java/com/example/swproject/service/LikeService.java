package com.example.swproject.service;

import com.example.swproject.domain.User;

import java.util.Set;

public interface LikeService {
    boolean toggleLike(Long placeId, User user);
    Set<Long> getLikedPlaceIdsByUser(User user);
}
