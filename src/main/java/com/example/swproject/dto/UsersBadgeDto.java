package com.example.swproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;

/**
 * DTO for {@link com.example.swproject.domain.UsersBadge}
 */
@Value
public class UsersBadgeDto implements Serializable {
  @NotNull
  Long id;

  @NotNull
  BadgeDto badge;
}