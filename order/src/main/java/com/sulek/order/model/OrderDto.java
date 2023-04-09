package com.sulek.order.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@Builder
@ToString
public class OrderDto {
    private Long orderId;
    private String orderedUserName;
    private String orderedUserAddress;
    private String orderStatus;
    private Long productId;
    private BigDecimal quantity;
}
