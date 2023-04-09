package com.sulek.order.model.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class OrderRequestDto {
    @JsonProperty("checkOrderRequestDto")
    private CheckOrderRequestDto checkOrderRequestDto;
}
