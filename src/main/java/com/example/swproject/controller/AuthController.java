package com.example.swproject.controller;

import com.example.swproject.dto.UserLoginDTO;
import com.example.swproject.dto.UserSignupDTO;
import com.example.swproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse as SwaggerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "회원가입", description = "사용자 계정 등록")
    @SwaggerResponse(responseCode = "200", description = "회원가입 성공")
    @PostMapping("/sign-up")
    public ResponseEntity<String> signup(@Valid @RequestBody UserSignupDTO dto) {
        userService.signup(dto);
        return ResponseEntity.ok("User registered successfully");
    }

    @Operation(summary = "로그인", description = "사용자 로그인")
    @SwaggerResponse(responseCode = "200", description = "로그인 성공")
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody UserLoginDTO dto) {
        String token = userService.login(dto);
        return ResponseEntity.ok(token);
    }

    @Operation(summary = "로그아웃", description = "사용자 로그아웃 (예: 클라이언트에서 토큰 제거)")
    @SwaggerResponse(responseCode = "200", description = "로그아웃 성공")
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        // 실제 토큰 무효화 로직 필요 시 추가
        return ResponseEntity.ok("Logged out successfully");
    }

    @Operation(summary = "내 정보 조회", description = "로그인한 사용자 정보 조회")
    @SwaggerResponse(responseCode = "200", description = "조회 성공")
    @GetMapping("/me")
    public ResponseEntity<String> me() {
        // JWT 기반이면 SecurityContextHolder에서 사용자 정보 가져오기
        return ResponseEntity.ok("User info endpoint (stub)");
    }
}
