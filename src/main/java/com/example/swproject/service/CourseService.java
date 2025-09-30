package com.example.swproject.service;

import com.example.swproject.domain.Place;

import java.util.List;

public interface CourseService {
    List<Place> recommendCourses(String fhash, String shash);
}
