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
@Table(name = "courses")
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "courses_id", nullable = false)
  private Long id;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "users_id", nullable = false)
  private User user;

  @NotNull
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "places_id", nullable = false)
  private Place place;

  @Size(max = 255)
  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @Size(max = 255)
  @NotNull
  @Column(name = "startaddress", nullable = false)
  private String startaddress;

  @NotNull
  @Column(name="startdate", nullable = false)
  private LocalDateTime startdate;

  @NotNull
  @Column(name="enddate", nullable = false)
  private LocalDateTime enddate;


  @NotNull
  @Column(name = "`order`", nullable = false)
  private Long order;

  @NotNull
  @Column(name = "nth", nullable = false)
  private Long nth;

}