package com.example.swproject.service;

import com.example.swproject.domain.Place;
import com.example.swproject.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CourseService {
    List<Place> recommendCourses(String fhash, String shash, List<Long> excludePlaceIds);

    void saveCourse(String courseName, String startAddress, LocalDateTime startDate, LocalDateTime endDate, List<Long> placeIds, User user);
}
