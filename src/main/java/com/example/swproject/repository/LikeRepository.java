package com.example.swproject.repository;

import com.example.swproject.domain.Like;
import com.example.swproject.domain.Place;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.swproject.domain.QLike.like;

@Repository
public class LikeRepository {

  @Autowired
  private SDJpaLikeRepository likeRepository;
  private EntityManager em;
  private JPAQueryFactory queryFactory;

  public LikeRepository(EntityManager em) {
    this.em = em;
    this.queryFactory = new JPAQueryFactory(em);
  }

  public Like save(Like like) {
    return likeRepository.save(like);
  }

  public List<Place> findPlaceByUserId(Long userId) {
    return queryFactory.select(like.place)
        .from(like)
        .where(like.user.id.eq(userId))
        .fetch();
  }

  public Optional<Like> findByUserAndPlace(Long userId, Long placeId) {
    return likeRepository.findByUserIdAndPlaceId(userId, placeId);
  }

  public void delete(Long userId, Long placeId){
    queryFactory.delete(like)
        .where(like.place.id.eq(placeId), like.user.id.eq(userId))
        .execute();
  }

}
