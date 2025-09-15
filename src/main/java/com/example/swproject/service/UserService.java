package com.example.swproject.service;

import com.example.swproject.domain.User;
import com.example.swproject.dto.UserLoginDTO;
import com.example.swproject.dto.UserSignupDTO;
import com.example.swproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(UserSignupDTO userSignupDTO) {
        if (userRepository.findByLoginId(userSignupDTO.getLoginId()).isPresent()) {
            throw new IllegalStateException("이미 존재하는 아이디입니다.");
        }

        User user = new User();
        user.setLoginId(userSignupDTO.getLoginId());
        user.setLoginPw(passwordEncoder.encode(userSignupDTO.getLoginPw()));
        user.setEmail(userSignupDTO.getEmail());
        user.setCountry(userSignupDTO.getCountry());
        user.setReligion(userSignupDTO.getReligion());
        user.setPhone(userSignupDTO.getPhone());
        user.setJob(userSignupDTO.getJob());
        user.setAge(userSignupDTO.getAge());

        userRepository.save(user);
    }

    public String login(UserLoginDTO userLoginDTO) {
        User user = userRepository.findByLoginId(userLoginDTO.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 아이디 입니다."));

        if (!passwordEncoder.matches(userLoginDTO.getLoginPw(), user.getLoginPw())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        return "dummy-jwt-token";
    }
}
