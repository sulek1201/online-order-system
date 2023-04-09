package com.sulek.seller.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequestDto {
    private String productName;
    private String description;
    private BigDecimal quantity;
}
