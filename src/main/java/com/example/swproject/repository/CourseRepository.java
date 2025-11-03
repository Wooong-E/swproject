package com.example.swproject.repository;

import com.example.swproject.domain.Course;
import com.example.swproject.domain.QCourse;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.example.swproject.domain.QCourse.course;

@Repository()
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


  public List<Course> findCourseByUsersIdAndNth(Long usersId,Long nth){
    return queryFactory.select(course)
        .from(course)
        .where(course.user.id.eq(usersId), course.nth.eq(nth))
        .orderBy(course.order.asc())
        .fetch();
  }

  public List<List<Course>> findAllCourseByUsersId(Long usersId){
    List<Course> courses = queryFactory.select(course)
        .from(course)
        .where(course.user.id.eq(usersId))
        .orderBy(course.nth.asc(), course.order.asc())
        .fetch();

    if (courses.isEmpty()) {
      return new ArrayList<>();
    }

    List<List<Course>> courseList=new ArrayList<>();

    Long nth=courses.get(0).getNth();
    List<Course> courseListComp=new ArrayList<>();

    for(Course course : courses){
      if(course.getNth().equals(nth)){
        courseListComp.add(course);
      }
      else{
        courseList.add(courseListComp);

        courseListComp=new ArrayList<>();
        courseListComp.add(course);
        nth=course.getNth();
      }
    }

    courseList.add(courseListComp);

    return courseList;
  }
  

  public Long findMaxNth(Long usersId){
    return queryFactory.select(course.nth.max())
        .from(course)
        .where(course.user.id.eq(usersId))
        .fetchOne();
  }

  public void delete(Long userId,Long nth){
    queryFactory.delete(course)
        .where(course.user.id.eq(userId), course.nth.eq(nth))
        .execute();
  }

  public Long countNthByUserId(Long userId) {
    return queryFactory.select(course.nth.countDistinct())
        .from(course)
        .where(course.user.id.eq(userId))
        .fetchOne();
  }

}
