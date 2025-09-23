package com.example.swproject.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.swproject.domain.User}
 */
@Value
public class UserDto implements Serializable {
  @NotNull
  @Size(max = 225)
  String loginId;

  @NotNull
  @Size(max = 225)
  String loginPw;

  @NotNull
  @Size(max = 225)
  String name;

  @NotNull
  @Size(max = 30)
  String email;

  @NotNull
  @Size(max = 30)
  String country;

  @Size(max = 30)
  String religion;

  @NotNull
  @Size(max = 30)
  String phone;

  @NotNull
  @Size(max = 30)
  String job;

  Long age;
}