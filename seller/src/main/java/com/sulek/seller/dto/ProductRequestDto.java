package com.sulek.seller.dto;

import lombok.Data;

@Data
public class ProductRequestDto {
    private String productName;
    private String description;
    private Long quantity;
}
