package com.example.swproject.repository;

import com.example.swproject.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SDJpaPlaceRepository extends JpaRepository<Place, Long> {
}