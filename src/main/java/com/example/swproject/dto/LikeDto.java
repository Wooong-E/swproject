package com.example.swproject.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

@Value
public class LikeDto implements Serializable {
    @NotNull
    Long userId;

    @NotNull
    Long placeId;
}
