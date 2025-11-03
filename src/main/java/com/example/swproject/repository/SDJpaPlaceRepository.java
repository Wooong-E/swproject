package com.example.swproject.repository;

import com.example.swproject.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SDJpaPlaceRepository extends JpaRepository<Place, Long> {
    List<Place> findByCategory(String category);
}