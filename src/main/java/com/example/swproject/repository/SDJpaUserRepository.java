package com.example.swproject.repository;

import com.example.swproject.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SDJpaUserRepository extends JpaRepository<User, Long> {

  Optional<User> findByLoginId(String loginId);

  Optional<User> findByName(String name);

  Optional<User> findByLoginIdAndLoginPw(String loginId, String password);
}