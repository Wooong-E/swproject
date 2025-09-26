package com.example.swproject.repository;

import com.example.swproject.domain.Review;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.swproject.domain.QPlace.place;
import static com.example.swproject.domain.QReview.review;

@Repository
public class ReviewRepository {

  @Autowired
  private SDJpaReviewRepository reviewRepository;
  private EntityManager em;
  private JPAQueryFactory queryFactory;

  public ReviewRepository(EntityManager em) {
    this.em = em;
    this.queryFactory = new JPAQueryFactory(em);
  }

  public Review save(Review review) {
    return reviewRepository.save(review);
  }

  public List<Review> findReviewByPlaceName(String placeName) {
    return queryFactory.select(review)
        .from(review)
        .join(review.place, place)
        .where(place.name.eq(placeName))
        .fetch();
  }

  public Double findAvgGrade(Long placesId){
    return queryFactory.select(review.grade.avg())
        .from(review)
        .where(review.place.id.eq(placesId))
        .fetchOne();
  }

  public Long findPlaceMaxOrder(Long placesId){
    return queryFactory.select(review.order.max())
        .from(review)
        .where(place.id.eq(placesId))
        .fetchOne();
  }

  public List<Review> findReviewByPlaceId(Long placeId) {
    return reviewRepository.findReviewByPlaceId(placeId);
  }
}
