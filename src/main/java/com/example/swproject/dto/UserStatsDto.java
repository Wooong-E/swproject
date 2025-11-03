package com.example.swproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserStatsDto {
    private long likedPlacesCount;
    private long courseCount;
    private long reviewCount;
    private String nearestCourseDate;
    private Long dDay;

    public UserStatsDto(long likedPlacesCount, long courseCount, long reviewCount) {
        this.likedPlacesCount = likedPlacesCount;
        this.courseCount = courseCount;
        this.reviewCount = reviewCount;
    }
}
