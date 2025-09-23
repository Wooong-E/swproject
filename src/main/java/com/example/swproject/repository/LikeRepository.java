package com.example.swproject.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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
}
