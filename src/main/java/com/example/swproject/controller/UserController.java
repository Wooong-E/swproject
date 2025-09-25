package com.example.swproject.controller;

import com.example.swproject.dto.UserLoginDTO;
import com.example.swproject.dto.UserSignupDTO;
import com.example.swproject.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
@Slf4j
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 폼 화면
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("UserSignupDTO", new UserSignupDTO(null, null, null, null, null, null, null, null, null));
        return "signup"; // src/main/resources/templates/signup.html
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute UserSignupDTO userSignupDTO,
                         BindingResult bindingResult,
                         Model model) {

        if (bindingResult.hasErrors()) {
            return "signup"; // 에러 있으면 다시 폼 화면
        }

        try {
            userService.signup(userSignupDTO);
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "signup";
        }

        return "redirect:/users/login"; // 회원가입 성공 후 로그인 페이지로
    }

    // 로그인 폼 화면
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("UserLoginDTO",new UserLoginDTO(null,null));
        return "login"; // src/main/resources/templates/login.html
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute UserLoginDTO userLoginDTO,
                        BindingResult bindingResult,
                        Model model) {

        if (bindingResult.hasErrors()) {
            return "login";
        }

        try {
            String token = userService.login(userLoginDTO);
            // 세션/쿠키에 token 저장하거나 Security 연동 가능
            model.addAttribute("token", token);
            return "redirect:/"; // 로그인 성공 후 홈 화면
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            log.info("error={}", e.getMessage());
            return "login";
        }
    }
}
