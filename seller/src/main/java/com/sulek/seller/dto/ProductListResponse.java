package com.sulek.seller.dto;


import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
@Builder
public class ProductListResponse {
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal quantity;
}
