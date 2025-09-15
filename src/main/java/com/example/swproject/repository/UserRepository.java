package com.example.swproject.repository;

import com.example.swproject.domain.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public class UserRepository {

  @Autowired
  private SDJpaUserRepository userRepository;


  public User save(User user) {
    return userRepository.save(user);
  }

  public Optional<User> findByLoginId(String loginId) {
    return userRepository.findByLoginId(loginId);
  }

  public Optional<User> findByName(String name) {
    return userRepository.findByName(name);
  }

  public Optional<User> findByLoginIdAndPassword(String loginId, String password) {
    return userRepository.findByLoginIdAndLoginPw(loginId,password);
  }

}
