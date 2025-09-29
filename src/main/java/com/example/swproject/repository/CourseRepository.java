package com.example.swproject.repository;

import com.example.swproject.domain.Course;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CourseRepository {

  @Autowired
  private SDJpaCourseRepository courseRepository;
  private EntityManager em;
  private JPAQueryFactory queryFactory;

  public CourseRepository(EntityManager em) {
    this.em = em;
    this.queryFactory = new JPAQueryFactory(em);
  }

  public Course save(Course course) {
    return courseRepository.save(course);
  }
}
