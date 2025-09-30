package com.example.swproject.service;

import com.example.swproject.domain.Place;
import com.example.swproject.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public Optional<Place> findById(Long id) {
        return placeRepository.findById(id);
    }
}
