package com.example.swproject.service;

import com.example.swproject.domain.Course;
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

        Course nearestCourse = courseRepository.findNearestCourse(user.getId());
        String nearestCourseDate = null;
        Long dDay = null;

        if (nearestCourse != null) {
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyy.MM.dd");
            nearestCourseDate = nearestCourse.getStartdate().format(formatter);
            dDay = java.time.temporal.ChronoUnit.DAYS.between(java.time.LocalDate.now(), nearestCourse.getStartdate().toLocalDate());
        }

        return new UserStatsDto(likedPlacesCount, courseCount, reviewCount, nearestCourseDate, dDay);
    }
}
