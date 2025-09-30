package com.example.swproject.repository;

import com.example.swproject.domain.Review;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

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

  public Double findAvgGrade(Long placesId) {
    return queryFactory.select(review.grade.avg())
        .from(review)
        .where(review.place.id.eq(placesId))
        .fetchOne();
  }

  public Long findPlaceMaxOrder(Long placesId) {
    return queryFactory.select(review.order.max())
        .from(review)
        .where(place.id.eq(placesId))
        .fetchOne();
  }

  public List<Review> findReviewByPlaceId(Long placeId) {
    return queryFactory.select(review)
        .from(review)
        .where(review.place.id.eq(placeId))
        .orderBy(review.order.asc())
        .fetch();
  }

  public Review findReviewByPlaceIdAndOrder(Long placeId, Long order) {
    return queryFactory.select(review)
        .from(review)
        .where(review.place.id.eq(placeId), review.order.eq(order))
        .fetchOne();
  }

  private List<Long> findTop2EqualHash(String fhash, String shash) {

    List<Tuple> fReviewCount = queryFactory.select(review.place.category, review.place.id, review.id.count())
        .from(review)
        .where(review.fhash.eq(fhash))
        .groupBy(review.place.category, review.place.id)
        .orderBy(review.place.id.asc())
        .fetch();

    List<Tuple> sReviewCount = queryFactory.select(review.place.category, review.place.id, review.id.count())
        .from(review)
        .where(review.shash.eq(shash))
        .groupBy(review.place.category, review.place.id)
        .orderBy(review.place.id.asc())
        .fetch();

    Map<String, List<Long>> topPlacesByCategory = combineAndRankByCategory(fReviewCount, sReviewCount);

    return topPlacesByCategory.values().stream()
        .flatMap(List::stream)
        .collect(Collectors.toList());



  }

  private Map<String, List<Long>> combineAndRankByCategory(List<Tuple> fReviewCount, List<Tuple> sReviewCount) {

    Map<String, Map<Long, Long>> combinedCounts = new HashMap<>();

    for (Tuple tuple : fReviewCount) {
      String category = tuple.get(place.category);
      Long placeId = tuple.get(place.id);
      Long count = tuple.get(2, Long.class);

      combinedCounts.computeIfAbsent(category, k -> new HashMap<>()).put(placeId, count);
    }

    for (Tuple tuple : sReviewCount) {
      String category = tuple.get(place.category);
      Long placeId = tuple.get(place.id);
      Long count = tuple.get(2, Long.class);

      combinedCounts.computeIfAbsent(category, k -> new HashMap<>()).merge(placeId, count, Long::sum);
    }

    Map<String, List<Long>> finalResult = new LinkedHashMap<>();

    combinedCounts.forEach((category, placeCountMap) -> {
      List<Long> top2PlaceIds = placeCountMap.entrySet().stream()
          // value(합산 개수) 기준으로 내림차순 정렬
          .sorted(Map.Entry.<Long, Long>comparingByValue().reversed())
          // 상위 2개만 선택
          .limit(2)
          // key(장소ID)만 추출
          .map(Map.Entry::getKey)
          .collect(Collectors.toList());

      finalResult.put(category, top2PlaceIds);
    });

    return finalResult;
  }

}
