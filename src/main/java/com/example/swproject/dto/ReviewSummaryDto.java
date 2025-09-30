package com.example.swproject.dto;

import com.example.swproject.domain.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
// *-detail-*.html 에서 요약된 리뷰들의 정보를 보여주기 위한 Dto
public class ReviewSummaryDto {
    private Review review;
    private String firstImageUrl;
}
