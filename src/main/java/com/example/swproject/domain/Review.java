package com.example.swproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reviews_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "users_id", nullable = false)
  private User user;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "places_id", nullable = false)
  private Place place;

  @Size(max = 500)
  @NotNull
  @Column(name = "content", nullable = false, length = 500)
  private String content;

  @Size(max = 255)
  @NotNull
  @Column(name = "title", nullable = false)
  private String title;

  @NotNull
  @Column(name = "grade", nullable = false)
  private Integer grade;

  @NotNull
  @Column(name = "`order`", nullable = false) // 실행 안돼서 중간에 '' 표시 넣었음 뺄거면 뺴도 됨 - 상범
  private Long order;

  @Size(max = 255)
  @Column(name = "fhash")
  private String fhash;

  @Size(max = 255)
  @Column(name = "shash")
  private String shash;

  @Column(name="created", nullable = false)
  private LocalDateTime created;


}