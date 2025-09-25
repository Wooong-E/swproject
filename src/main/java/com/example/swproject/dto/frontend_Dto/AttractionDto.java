package com.example.SWFRONT;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class AttractionDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private String price;
    private String transportation;
    private boolean isLiked;
    private List<ReviewDto> reviews;
}
