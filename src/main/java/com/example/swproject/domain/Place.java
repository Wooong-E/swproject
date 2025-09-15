package com.example.swproject.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "places")
public class Place {
  @Id
  @Column(name = "places_id", nullable = false)
  private Long id;

  @Size(max = 255)
  @NotNull
  @Column(name = "name", nullable = false)
  private String name;

  @Size(max = 255)
  @NotNull
  @Column(name = "address", nullable = false)
  private String address;

  @Size(max = 30)
  @Column(name = "phone", length = 30)
  private String phone;

  @Size(max = 60)
  @NotNull
  @Column(name = "time", nullable = false, length = 60)
  private String time;

  @Size(max = 255)
  @NotNull
  @Column(name = "comment", nullable = false)
  private String comment;

  @Size(max = 20)
  @NotNull
  @Column(name = "category", nullable = false, length = 20)
  private String category;

  @NotNull
  @Column(name = "menu", nullable = false)
  @JdbcTypeCode(SqlTypes.JSON)
  private Map<String, Object> menu;

  @Size(max = 255)
  @NotNull
  @Column(name = "place_image_url", nullable = false)
  private String placeImageUrl;

}