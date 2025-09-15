package com.example.swproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "users_badges")
public class UsersBadge {
  @Id
  @Column(name = "users_badge_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "users_id", nullable = false)
  private User users;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "badges_id", nullable = false)
  private Badge badges;

  @NotNull
  @Column(name = "date_time", nullable = false)
  private Instant dateTime;

}