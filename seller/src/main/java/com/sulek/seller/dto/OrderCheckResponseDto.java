package com.sulek.seller.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderCheckResponseDto {
    @JsonProperty("checkStatus")
    private Boolean checkStatus;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("sellerId")
    private Long sellerId;
}
