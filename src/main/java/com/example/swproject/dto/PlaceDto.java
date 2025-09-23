package com.example.swproject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.swproject.domain.Place}
 */
@Value
public class PlaceDto implements Serializable {
  @NotNull
  @Size(max = 255)
  String name;
}