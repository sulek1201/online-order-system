package com.sulek.seller.dto;


import lombok.Data;

import java.util.List;

@Data
public class OrderResponseDto {
    private Long orderId;
    private String orderedUserName;
    private String orderedUserAddress;
    private List<ProductOrderDto> orderedProductList;
}
