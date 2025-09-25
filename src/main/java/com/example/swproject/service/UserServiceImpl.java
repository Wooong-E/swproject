package com.example.swproject.service;

import com.example.swproject.domain.User;
import com.example.swproject.dto.UserLoginDTO;
import com.example.swproject.dto.UserSignupDTO;
import com.example.swproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // PasswordEncoder 주입

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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
        // 비밀번호를 암호화하여 저장
        user.setLoginPw(passwordEncoder.encode(dto.getLoginPw()));
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
        // 아이디로 사용자를 먼저 조회
        User user = userRepository.findByLoginId(dto.getLoginId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid login ID or password"));

        // 입력된 비밀번호와 DB의 암호화된 비밀번호를 비교
        if (!passwordEncoder.matches(dto.getLoginPw(), user.getLoginPw())) {
            throw new IllegalArgumentException("Invalid login ID or password");
        }

        // UUID 기반 간단 토큰 반환 (테스트용)
        return UUID.randomUUID().toString();
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // *** 여기가 핵심 ***
        // 파라미터 이름은 'username'이지만, 실제 내용은 'loginId' 입니다.
        // 이 변수(username)를 사용해 loginId로 사용자를 찾습니다.
        User user = userRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("아이디를 찾을 수 없습니다: " + username));

        // 찾은 사용자 정보를 UserDetails 타입으로 반환합니다.
        return user;
    }
}
