package com.example.swproject.service;

import com.example.swproject.domain.User;
import com.example.swproject.dto.UserLoginDTO;
import com.example.swproject.dto.UserSignupDTO;
import com.example.swproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void signup(UserSignupDTO dto) {
        // 로그인 ID 중복 체크
        if (userRepository.findByLoginId(dto.getLoginId()).isPresent()) {
            throw new IllegalArgumentException("Login ID already exists");
        }

        // 이름 중복 체크 (선택)
        if (userRepository.findByName(dto.getName()).isPresent()) {
            throw new IllegalArgumentException("Name already exists");
        }

        // User 엔티티 생성
        User user = new User();
        user.setId(null); // auto-generated
        user.setLoginId(dto.getLoginId());
        user.setLoginPw(dto.getLoginPw()); // plain text -> 보안 추가할 예정.
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setCountry(dto.getCountry());
        user.setReligion(dto.getReligion());
        user.setPhone(dto.getPhone());
        user.setJob(dto.getJob());
        user.setAge(dto.getAge());

        userRepository.save(user);
    }

    @Override
    public String login(UserLoginDTO dto) {
        Optional<User> optionalUser = userRepository.findByLoginIdAndPassword(dto.getLoginId(), dto.getLoginPw());

        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("Invalid login ID or password");
        }

        // UUID 기반 간단 토큰 반환 (테스트용)
        return UUID.randomUUID().toString();
    }
}
