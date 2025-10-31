package com.example.swproject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PlaceDto {
    private Long id;
    private String name;
    private String address;
    private String category;
}
