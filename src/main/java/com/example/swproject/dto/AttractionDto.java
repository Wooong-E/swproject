package com.example.swproject.dto;

import lombok.Data;

// front 에서 장소 세부 카테고리별 장소를 보여주기 위한 Dto.
@Data
public class AttractionDto {
    private Long place_code;
    private String name;
    private String location;
    private String category;
    private String main_image;
}
