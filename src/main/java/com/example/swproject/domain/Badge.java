package com.example.swproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "badges")
public class Badge {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "badges_id", nullable = false)
  private Long id;

  @Size(max = 255)
  @NotNull
  @Column(name = "badge_name", nullable = false)
  private String badgeName;

  @Size(max = 255)
  @NotNull
  @Column(name = "badge_image_url", nullable = false)
  private String badgeImageUrl;

}