package com.example.swproject.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reports")
public class Report {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "reports_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "users_id", nullable = false)
  private User user;

  @Size(max = 255)
  @NotNull
  @Column(name = "title", nullable = false)
  private String title;

  @Size(max = 500)
  @NotNull
  @Column(name = "content", nullable = false, length = 500)
  private String content;

}