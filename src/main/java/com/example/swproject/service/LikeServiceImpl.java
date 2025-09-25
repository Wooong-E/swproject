package com.example.swproject.service;

import com.example.swproject.domain.Like;
import com.example.swproject.domain.Place;
import com.example.swproject.domain.User;
import com.example.swproject.repository.LikeRepository;
import com.example.swproject.repository.PlaceRepository;
import com.example.swproject.repository.SDJpaLikeRepository;
import com.example.swproject.repository.SDJpaPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
}
