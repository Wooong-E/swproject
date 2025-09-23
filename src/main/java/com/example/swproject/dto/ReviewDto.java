package com.example.swproject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.swproject.domain.Review}
 */
@Value
public class ReviewDto implements Serializable {
  @NotNull
  UserDto user;
  @NotNull
  PlaceDto place;
  @NotNull
  @Size(max = 500)
  String content;
  @NotNull
  Integer grade;
  @Size(max = 255)
  String visitedImageUrl;
}