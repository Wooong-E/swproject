package com.example.swproject.repository;

import com.example.swproject.domain.Place;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PlaceRepository {

  @Autowired
  private SDJpaPlaceRepository placeRepository;
  private EntityManager em;
  private JPAQueryFactory queryFactory;

  public PlaceRepository(EntityManager em) {
    this.em = em;
    this.queryFactory = new JPAQueryFactory(em);
  }

  public Place save(Place place) {
    return placeRepository.save(place);
  }

  public Optional<Place> findById(Long id) {
    return placeRepository.findById(id);
  }

}
