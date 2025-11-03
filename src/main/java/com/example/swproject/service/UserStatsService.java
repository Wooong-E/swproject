package com.example.swproject.service;

import com.example.swproject.domain.User;
import com.example.swproject.dto.UserStatsDto;

public interface UserStatsService {
    UserStatsDto getUserStats(User user);
}
