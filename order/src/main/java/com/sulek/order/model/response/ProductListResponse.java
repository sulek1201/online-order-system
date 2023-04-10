package com.sulek.order.model.response;


import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class ProductListResponse {
    private String name;
    private String description;
    private BigDecimal price;
    private BigDecimal quantity;
}
