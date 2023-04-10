package com.sulek.order.model.response;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@Builder
@ToString
public class OrderListResponse {
    private ProductListResponse productListResponse;
    private Date orderedDate;
    private String orderStatus;
}
