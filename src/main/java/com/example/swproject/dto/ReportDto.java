package com.example.swproject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.swproject.domain.Report}
 */
@Value
public class ReportDto implements Serializable {
  @NotNull
  Long users_id;

  @NotNull
  @Size(max = 255)
  String title;

  @NotNull
  @Size(max = 500)
  String content;
}