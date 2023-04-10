package com.sulek.order.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApproveOrderRepsonse {
    @JsonProperty("checkStatus")
    private Boolean checkStatus;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("quantity")
    private BigDecimal quantity;
    @JsonProperty("productId")
    private Long productId;
}
