package com.example.swproject.service;

import com.example.swproject.domain.User;

public interface LikeService {
    boolean toggleLike(Long placeId, User user);
}
