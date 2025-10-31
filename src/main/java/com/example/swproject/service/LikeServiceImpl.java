package com.example.swproject.service;

import com.example.swproject.domain.Like;
import com.example.swproject.domain.Place;
import com.example.swproject.domain.User;
import com.example.swproject.repository.LikeRepository;
import com.example.swproject.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final PlaceRepository placeRepository;

    @Override
    public boolean toggleLike(Long placeId, User user) {
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid place Id:" + placeId));

        Optional<Like> existingLike = likeRepository.findByUserAndPlace(user.getId(), placeId);

        if (existingLike.isPresent()) {
            likeRepository.delete(user.getId(), placeId);

            return false; // "unliked"
        } else {
            Like newLike = new Like();
            newLike.setUser(user);
            newLike.setPlace(place);
            likeRepository.save(newLike);

            return true; // "liked"
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Long> getLikedPlaceIdsByUser(User user) {
        return likeRepository.findPlaceByUserId(user.getId())
            .stream()
            .map(Place::getId)
            .collect(Collectors.toSet());
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, List<Place>> getLikedPlacesCategorized(User user) {
        List<Place> likedPlaces = likeRepository.findPlaceByUserId(user.getId());

        List<Place> attractions = new ArrayList<>();
        List<Place> restaurants = new ArrayList<>();
        List<Place> cafes = new ArrayList<>();

        for (Place place : likedPlaces) {
            Long id = place.getId();
            if (id >= 1 && id <= 6) {
                attractions.add(place);
            } else if (id >= 7 && id <= 12) {
                restaurants.add(place);
            } else if (id >= 13 && id <= 18) {
                cafes.add(place);
            }
        }

        Map<String, List<Place>> categorizedLikes = new HashMap<>();
        categorizedLikes.put("attraction", attractions);
        categorizedLikes.put("restaurant", restaurants);
        categorizedLikes.put("cafe", cafes);

        return categorizedLikes;
    }
}
