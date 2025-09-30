package com.example.swproject.repository;

import com.example.swproject.domain.Course;
import com.example.swproject.domain.QCourse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.example.swproject.domain.QCourse.course;

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

  public List<Long> findCourseByNth(Long usersId, Long nth){
    List<Course> nthCourse = queryFactory.select(course)
        .from(course)
        .where(course.user.id.eq(usersId), course.nth.eq(nth))
        .orderBy(course.order.asc())
        .fetch();

    List<Long> placeOrder=new ArrayList<>();

    for(Course course : nthCourse){
      placeOrder.add(course.getPlace().getId());
    }

    return placeOrder;
  }

  public Long findMaxNth(Long usersId){
    return queryFactory.select(course.nth.max())
        .from(course)
        .where(course.user.id.eq(usersId))
        .fetchOne();
  }

  public void delete(Long nth){
    queryFactory.delete(course)
        .where(course.nth.eq(nth))
        .execute();
  }

}
