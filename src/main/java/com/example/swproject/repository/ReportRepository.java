package com.example.swproject.repository;

import com.example.swproject.domain.QReport;
import com.example.swproject.domain.Report;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.swproject.domain.QReport.report;

@Repository
public class ReportRepository {

  @Autowired
  private SDJpaReportRepository reportRepository;
  private EntityManager em;
  private JPAQueryFactory queryFactory;

  public ReportRepository(EntityManager em) {
    this.em = em;
    this.queryFactory = new JPAQueryFactory(em);
  }

  public Report save(Report report) {
    return reportRepository.save(report);
  }

  public List<Report> findByUsersId(Long usersId) {
    return queryFactory.select(report)
        .from(report)
        .where(report.user.id.eq(usersId))
        .fetch();
  }

}
