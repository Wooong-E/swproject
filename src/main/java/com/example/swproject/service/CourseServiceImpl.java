package com.example.swproject.service;

import com.example.swproject.domain.Place;
import com.example.swproject.repository.CourseRepository;
import com.example.swproject.repository.PlaceRepository;
import com.example.swproject.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {

    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Place> recommendCourses(String fhash, String shash) {
        List<Long> recommendedPlaceIds = reviewRepository.findTop2EqualHash(fhash, shash);

        return recommendedPlaceIds.stream()
                .map(placeRepository::findById)
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .collect(Collectors.toList());
    }
}
