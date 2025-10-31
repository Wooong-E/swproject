package com.example.swproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reports_post")
public class ReportsPost {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reports_post_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "reports_id", nullable = false)
  private Report report;

  @Size(max = 255)
  @NotNull
  @Column(name = "post_image_url", nullable = false)
  private String postImageUrl;

}