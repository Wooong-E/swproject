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
import org.springframework.web.bind.support.SessionStatus;

@Controller
@RequestMapping("/users")
@Slf4j
@SessionAttributes("userSignupDTO")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 세션에 저장할 DTO 객체를 모델에 추가
    @ModelAttribute("userSignupDTO")
    public UserSignupDTO setupForm() {
        return new UserSignupDTO("","","","","","");
    }

    // 1단계 폼 화면 (이름 입력)
    @GetMapping("/signup")
    public String signupForm(Model model) {
        // @ModelAttribute 덕분에 DTO는 자동으로 모델에 담겨있음
        return "signup";
    }

    // 1단계 처리 및 2단계 폼 요청
    // th:action="@{/users/signup/step2}"의 POST 요청을 처리
    @PostMapping("/signup/step2")
    public String processStep1(@ModelAttribute("userSignupDTO") UserSignupDTO userSignupDTO) {
        // Step 1의 데이터(name)가 userSignupDTO에 바인딩되어 세션에 자동 저장됨
        return "redirect:/users/signup/step2"; // GET으로 리다이렉트하여 뷰를 보여줌

    }

    @GetMapping("/signup/step2")
    public String signupFormStep2() {
        return "signup2"; // Step 2 템플릿 이름
    }

    // 2단계 처리 및 3단계 폼 요청
    @PostMapping("/signup/step3")
    public String processStep2(@ModelAttribute("userSignupDTO") UserSignupDTO userSignupDTO) {
        // Step 2의 데이터(country, religion)가 userSignupDTO에 바인딩되고 세션에 자동 저장됨
        return "redirect:/users/signup/step3";
    }

    @GetMapping("/signup/step3")
    public String signupFormStep3() {
        return "signup3"; // Step 3 템플릿 이름
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("userSignupDTO") UserSignupDTO userSignupDTO,
                       BindingResult bindingResult,
                       Model model,
                       SessionStatus sessionStatus)
    {
        // 1. 유효성 검사 오류가 있으면 즉시 폼을 다시 보여줍니다.
        if (bindingResult.hasErrors()) {
            // 이 경우, Spring이 자동으로 오류 메시지들을 모델에 담아줍니다.
            return "signup3";
        }

        try{
            userService.signup(userSignupDTO);
            sessionStatus.setComplete();

        }catch (Exception e){
            if(e.getMessage().equals("Login ID already exists")){
                // 2. ID 중복 오류 시, 오류 메시지를 모델에 담고 폼을 반환합니다.
                model.addAttribute("loginIdError","이미 사용중인 ID입니다.");
                return "signup3"; // <--- 여기서는 loginIdError만 발생했으므로 정상 반환
            }
            // 예측하지 못한 다른 예외가 발생하면 로그인 페이지로 리다이렉트합니다.
            log.error("Unhandled exception during signup: {}", e.getMessage());
        }

        // 성공적으로 회원가입을 마쳤을 때만 로그인 페이지로 리다이렉트
        return "redirect:/users/login";
    }

    /*// 회원가입 폼 화면
    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("name",null);
        return "signup"; // src/main/resources/templates/signup.html
    }

    @PostMapping("/signup/step2")
    public String signup2(@RequestParam String name, Model model) {
        model.addAttribute("name",name);
        model.addAttribute("country",null);
        model.addAttribute("religion",null);

        return "signup3"; // 회원가입 성공 후 로그인 페이지로
    }

    @PostMapping("/signup/step3")
    public String signup3(@RequestParam String name, @RequestParam String country, @RequestParam String religion,
                          Model model) {
        model.addAttribute("name",name);
        model.addAttribute("country",country);
        model.addAttribute("religion",religion);
        model.addAttribute("loginId",null);
        model.addAttribute("email",null);
        model.addAttribute("loginPw",null);

        return "signup3"; // 회원가입 성공 후 로그인 페이지로
    }

    @PostMapping("/save")
    public String save(@RequestParam String name, @RequestParam String country, @RequestParam String religion,
                       @RequestParam String email, @RequestParam String loginId, @RequestParam String loginPw, Model model)
    {
        UserSignupDTO userSignupDTO=new UserSignupDTO(name,country,religion,email,loginId,loginPw);
        try{
            userService.signup(userSignupDTO);
        }catch (Exception e){
            if(e.getMessage().equals("Login ID already exists")){
                model.addAttribute("loginIdError","이미 사용중인 Id입니다.");
                return "signup3";
            }
        }

        return "redirect:/users/login";
    }*/


    // 로그인 폼 화면
    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("UserLoginDTO",new UserLoginDTO(null,null));
        return "login"; // src/main/resources/templates/login.html
    }

    // 로그인 처리
    /*@PostMapping("/login")
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
    }*/
}
