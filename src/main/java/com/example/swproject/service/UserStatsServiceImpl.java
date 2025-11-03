package com.example.swproject.service;

import com.example.swproject.domain.User;
import com.example.swproject.dto.UserStatsDto;
import com.example.swproject.repository.CourseRepository;
import com.example.swproject.repository.LikeRepository;
import com.example.swproject.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserStatsServiceImpl implements UserStatsService {

    private final LikeRepository likeRepository;
    private final CourseRepository courseRepository;
    private final ReviewRepository reviewRepository;

    @Override
    public UserStatsDto getUserStats(User user) {
        long likedPlacesCount = likeRepository.countByUser(user);
        long courseCount = courseRepository.countNthByUserId(user.getId());
        long reviewCount = reviewRepository.countByUser(user);
        return new UserStatsDto(likedPlacesCount, courseCount, reviewCount);
    }
}
