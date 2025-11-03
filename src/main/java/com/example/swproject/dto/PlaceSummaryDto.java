package com.example.swproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlaceSummaryDto {
    private Long id;
    private String name;
    private String category;
    private Double averageGrade;
}
