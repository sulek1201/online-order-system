package com.sulek.order.model.request;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.sulek.order.model.ProductOrderDto;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckOrderRequestDto {
    @NotNull
    @JsonProperty("productList")
    private List<ProductOrderDto> productOrderDtoList;
}
