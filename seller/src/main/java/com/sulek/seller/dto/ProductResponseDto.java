package com.sulek.seller.dto;


import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponseDto {
    private BigDecimal quantity;
    private String name;
    private String description;
}
