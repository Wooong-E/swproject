package com.example.swproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reviews")
public class Review {
  @Id
  @Column(name = "reviews_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "users_id", nullable = false)
  private User users;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "places_id", nullable = false)
  private Place places;

  @Size(max = 500)
  @NotNull
  @Column(name = "content", nullable = false, length = 500)
  private String content;

  @NotNull
  @Column(name = "grade", nullable = false)
  private Integer grade;

  @Size(max = 255)
  @Column(name = "visited_image_url")
  private String visitedImageUrl;

}