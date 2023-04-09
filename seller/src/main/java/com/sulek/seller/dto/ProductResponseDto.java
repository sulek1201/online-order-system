package com.sulek.seller.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponseDto {
    private Long quantity;
    private String name;
    private String description;
}
