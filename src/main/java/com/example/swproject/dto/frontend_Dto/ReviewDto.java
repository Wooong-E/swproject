package com.example.SWFRONT;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ReviewDto {
    private Long reviewId;
    private String reviewImageUrl;
    private String reviewTitle;
    private int rating; // 1 to 5
}
