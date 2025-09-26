package com.example.swproject.repository;

import com.example.swproject.domain.QReviewsPost;
import com.example.swproject.domain.ReportsPost;
import com.example.swproject.domain.ReviewsPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.swproject.domain.QReportsPost.reportsPost;
import static com.example.swproject.domain.QReviewsPost.reviewsPost;

@Repository
public class ReviewsPostRepository {

  @Autowired
  private SDJpaReviewsPostRepository reviewsPostRepository;
  private EntityManager em;
  private JPAQueryFactory queryFactory;

  public ReviewsPostRepository(EntityManager em) {
    this.em = em;
    this.queryFactory = new JPAQueryFactory(em);
  }

  public ReviewsPost save(ReviewsPost reviewsPost) {
    return reviewsPostRepository.save(reviewsPost);
  }

  public List<ReviewsPost> findByReviewsId(Long reviewsId) {
    return queryFactory.select(reviewsPost)
        .from(reviewsPost)
        .where(reviewsPost.review.id.eq(reviewsId))
        .fetch();

  }

}
