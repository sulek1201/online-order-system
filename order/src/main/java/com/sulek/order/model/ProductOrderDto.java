package com.sulek.order.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductOrderDto {
    @JsonProperty("productId")
    private Long productId;
    @JsonProperty("amount")
    private Long amount;
    @JsonProperty("productPrice")
    private BigDecimal productPrice;
}
