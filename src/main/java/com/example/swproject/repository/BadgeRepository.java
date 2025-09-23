package com.example.swproject.repository;

import com.example.swproject.domain.Badge;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BadgeRepository {

  @Autowired
  private SDJpaBadgeRepository badgeRepository;
  private EntityManager em;
  private JPAQueryFactory queryFactory;

  public BadgeRepository(EntityManager em) {
    this.em = em;
    this.queryFactory = new JPAQueryFactory(em);
  }

  public Optional<Badge> findBadgeByBadgeName(String badgeName) {
    return findBadgeByBadgeName(badgeName);
  }




}
