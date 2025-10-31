package com.example.swproject.service;

import com.example.swproject.domain.Course;
import com.example.swproject.domain.Place;
import com.example.swproject.domain.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface CourseService {
    List<Place> recommendCourses(String fhash, String shash, List<Long> excludePlaceIds);

    void saveCourse(String courseName, String startAddress, LocalDateTime startDate, LocalDateTime endDate, List<Long> placeIds, User user);

    List<List<Course>> getAllCourses(User user);

    List<Course> getCourse(User user, Long nth);

    void deleteCourse(User user, Long nth);
}
