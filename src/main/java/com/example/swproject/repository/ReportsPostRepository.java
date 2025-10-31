package com.example.swproject.repository;

import com.example.swproject.domain.QReportsPost;
import com.example.swproject.domain.ReportsPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.swproject.domain.QReportsPost.*;

@Repository
public class ReportsPostRepository {

  @Autowired
  private SDJpaReportsPostRepository reportsPostRepository;
  private EntityManager em;
  private JPAQueryFactory queryFactory;

  public ReportsPostRepository(EntityManager em) {
    this.em = em;
    this.queryFactory = new JPAQueryFactory(em);
  }

  public ReportsPost save(ReportsPost reportsPost) {
    return reportsPostRepository.save(reportsPost);
  }

  public List<ReportsPost> findByReportsId(Long reportsId) {
    return queryFactory.select(reportsPost)
        .from(reportsPost)
        .where(reportsPost.report.id.eq(reportsId))
        .fetch();

  }

}
