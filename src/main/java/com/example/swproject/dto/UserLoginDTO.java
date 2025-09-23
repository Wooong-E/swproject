package com.example.swproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.swproject.domain.User}
 */
@Value
public class UserLoginDTO implements Serializable {

  @NotBlank(message = "아이디는 필수 입력값입니다.")
  @Size(min = 4, max = 225, message = "아이디는 최소 4자 이상, 225자 이하여야 합니다.")
  String loginId;

  @NotBlank(message = "비밀번호는 필수 입력값입니다.")
  @Size(min = 8, max = 225, message = "비밀번호는 최소 8자 이상이어야 합니다.")
  String loginPw;
}
