package com.example.swproject.service;

import com.example.swproject.dto.UserLoginDTO;
import com.example.swproject.dto.UserSignupDTO;

public interface UserService {
    void signup(UserSignupDTO dto);
    String login(UserLoginDTO dto);
}
