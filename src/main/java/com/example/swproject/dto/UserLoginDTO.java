package com.example.swproject.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.example.swproject.domain.User}
 */

@Value
public class UserLoginDTO implements Serializable {
  @NotNull
  @Size(max = 225)
  @NotEmpty
  String loginId;

  @NotNull
  @Size(max = 225)
  @NotEmpty
  String loginPw;
}