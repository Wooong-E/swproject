package com.example.swproject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
  @Id
  @Column(name = "users_id", nullable = false)
  private Long id;

  @Size(max = 225)
  @NotNull
  @Column(name = "login_id", nullable = false, length = 225)
  private String loginId;

  @Size(max = 225)
  @NotNull
  @Column(name = "login_pw", nullable = false, length = 225)
  private String loginPw;

  @Size(max = 225)
  @NotNull
  @Column(name = "name", nullable = false, length = 225)
  private String name;

  @Size(max = 30)
  @NotNull
  @Column(name = "email", nullable = false, length = 30)
  private String email;

  @Size(max = 30)
  @NotNull
  @Column(name = "country", nullable = false, length = 30)
  private String country;

  @Size(max = 30)
  @Column(name = "religion", length = 30)
  private String religion;

  @Size(max = 30)
  @NotNull
  @Column(name = "phone", nullable = false, length = 30)
  private String phone;

  @Size(max = 30)
  @NotNull
  @Column(name = "job", nullable = false, length = 30)
  private String job;

  @Column(name = "age", columnDefinition = "int UNSIGNED not null")
  private Long age;

}