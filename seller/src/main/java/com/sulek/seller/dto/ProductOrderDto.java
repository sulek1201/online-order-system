package com.sulek.seller.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductOrderDto {
    @JsonProperty("productId")
    private Long productId;
    @JsonProperty("quantity")
    private BigDecimal quantity;

}
