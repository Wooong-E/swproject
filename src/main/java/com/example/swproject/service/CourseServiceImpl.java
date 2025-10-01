package com.example.swproject.service;

import com.example.swproject.domain.Course;
import com.example.swproject.domain.Place;
import com.example.swproject.domain.User;
import com.example.swproject.repository.CourseRepository;
import com.example.swproject.repository.PlaceRepository;
import com.example.swproject.repository.ReviewRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.IntStream.range;

@Service
@RequiredArgsConstructor
@Transactional
public class CourseServiceImpl implements CourseService {

    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;
    private final CourseRepository courseRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Place> recommendCourses(String fhash, String shash, List<Long> excludePlaceIds) {
        List<Long> recommendedPlaceIds = reviewRepository.findTop2EqualHash(fhash, shash);

        // If excludePlaceIds is not null, filter the recommendedPlaceIds
        if (excludePlaceIds != null && !excludePlaceIds.isEmpty()) {
            recommendedPlaceIds = recommendedPlaceIds.stream()
                    .filter(id -> !excludePlaceIds.contains(id))
                    .collect(Collectors.toList());
        }

        return recommendedPlaceIds.stream()
                .filter(java.util.Objects::nonNull) // Filter out null IDs
                .map(placeRepository::findById)
                .filter(java.util.Optional::isPresent)
                .map(java.util.Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    public void saveCourse(String courseName, String startAddress, LocalDateTime startDate, LocalDateTime endDate, List<Long> placeIds, User user) {
        // 1. 새로운 nth 값 계산
        Long maxNth = courseRepository.findMaxNth(user.getId());
        long newNth;
        if (maxNth != null) {
            newNth = maxNth + 1;
        } else {
            newNth = 1L;
        }

        // 2. 장소 목록을 순회하며 Course 엔티티 저장 (IntStream 으로 변경)
        range(0, placeIds.size()).forEach(i -> {
            long order = i + 1;
            Long placeId = placeIds.get(i); // 각 반복마다 새로운 변수로 고정
            Place place = placeRepository.findById(placeId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid place Id:" + placeId));

            Course course = new Course();
            course.setUser(user);
            course.setPlace(place);
            course.setName(courseName);
            course.setStartaddress(startAddress);
            course.setStartdate(startDate);
            course.setEnddate(endDate);
            course.setOrder(order);
            course.setNth(newNth);

            courseRepository.save(course);
        });
    }

    public List<Course> getCourse(User user, Long nth) {
        return courseRepository.findCourseByUsersIdAndNth(user.getId(), nth);
    }

    public List<List<Course>> getAllCourses(User user){
        return courseRepository.findAllCourseByUsersId(user.getId());
    }

    public void deleteCourse(User user, Long nth){
        courseRepository.delete(user.getId(),nth);
    }

}
