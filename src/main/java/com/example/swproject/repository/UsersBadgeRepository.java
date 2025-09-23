package com.example.swproject.repository;


import com.example.swproject.domain.Badge;
import com.example.swproject.domain.UsersBadge;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static com.example.swproject.domain.QBadge.badge;
import static com.example.swproject.domain.QUsersBadge.usersBadge;

@Repository
public class UsersBadgeRepository {

  @Autowired
  private SDJpaUsersBadgeRepository usersBadgeRepository;
  private EntityManager em;
  private JPAQueryFactory queryFactory;

  public UsersBadgeRepository(EntityManager em) {
    this.em = em;
    this.queryFactory = new JPAQueryFactory(em);
  }

  public List<String> findAllBadges(Long userId){
    List<Badge> badges = queryFactory.select(usersBadge.badge)
        .from(usersBadge)
        .join(usersBadge.badge, badge)
        .where(usersBadge.user.id.eq(userId))
        .fetch();

    List<String> badges_url=new ArrayList<>();

    for(Badge badge : badges){
      badges_url.add(badge.getBadgeImageUrl());
    }

    return badges_url;
  }

  public void deleteBadge(Long userId){
    usersBadgeRepository.deleteById(userId);
  }

  public void save(UsersBadge usersBadge){
    usersBadgeRepository.save(usersBadge);
  }
}
