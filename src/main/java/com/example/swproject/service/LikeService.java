package com.example.swproject.service;

import com.example.swproject.domain.Place;
import com.example.swproject.domain.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface LikeService {
    boolean toggleLike(Long placeId, User user);
    Set<Long> getLikedPlaceIdsByUser(User user);
    Map<String, List<Place>> getLikedPlacesCategorized(User user);
}
