package com.example.swproject.repository;

import com.example.swproject.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SDJpaCourseRepository extends JpaRepository<Course, Long> {
}