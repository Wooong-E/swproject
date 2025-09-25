package com.example.SWFRONT;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class RestaurantDto {
    private Long id;
    private String name;
    private String description;
    private String imageUrl; // Main image for the restaurant
    private String address; // Changed from price in AttractionDto, more relevant for restaurant
    private String transportation;
    private boolean isLiked;
    private List<ReviewDto> reviews;
    private List<MenuDto> menuList; // New field for menu items
}
