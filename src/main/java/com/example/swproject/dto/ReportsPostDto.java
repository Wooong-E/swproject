package com.example.swproject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.swproject.domain.ReportsPost}
 */
@Value
public class ReportsPostDto implements Serializable {
  @NotNull
  Long reports_id;

  @NotNull
  @Size(max = 255)
  String postImageUrl;
}