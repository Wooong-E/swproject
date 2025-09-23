package com.example.swproject.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.swproject.domain.User}
 */
@Value
public class UserSignupDTO implements Serializable {

  @NotBlank(message = "아이디는 필수 입력값입니다.")
  @Size(min = 4, max = 225, message = "아이디는 최소 4자 이상, 225자 이하여야 합니다.")
  String loginId;

  @NotBlank(message = "비밀번호는 필수 입력값입니다.")
  @Size(min = 8, max = 225, message = "비밀번호는 최소 8자 이상이어야 합니다.")
  @Pattern(
          regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&]).{8,}$",
          message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다."
  )
  String loginPw;

  @NotBlank(message = "이름은 필수 입력값입니다.")
  @Size(max = 225, message = "이름은 225자 이하여야 합니다.")
  String name;

  @NotBlank(message = "이메일은 필수 입력값입니다.")
  @Email(message = "올바른 이메일 주소를 입력해주세요.")
  String email;

  @NotBlank(message = "국가는 필수 입력값입니다.")
  @Size(max = 30, message = "국가 이름은 30자 이하여야 합니다.")
  String country;

  @Size(max = 30, message = "종교 정보는 30자 이하여야 합니다.")
  String religion;

  @NotBlank(message = "전화번호는 필수 입력값입니다.")
  @Pattern(regexp = "^[0-9]{10,15}$", message = "전화번호는 숫자 10~15자리여야 합니다.")
  String phone;

  @NotBlank(message = "직업은 필수 입력값입니다.")
  @Size(max = 30, message = "직업은 30자 이하여야 합니다.")
  String job;

  @NotNull(message = "나이는 필수 입력값입니다.")
  @Min(value = 1, message = "나이는 1세 이상이어야 합니다.")
  @Max(value = 120, message = "나이는 120세 이하이어야 합니다.")
  Long age;
}
