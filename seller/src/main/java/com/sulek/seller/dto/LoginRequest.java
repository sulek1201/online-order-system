package com.sulek.seller.dto;

import com.sun.istack.NotNull;
import lombok.Data;


@Data
public class LoginRequest {
    @NotNull
    private String businessName;
    @NotNull
    private String password;
}
