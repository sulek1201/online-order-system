package com.sulek.order.dto;

import com.sun.istack.NotNull;
import lombok.Data;


@Data
public class LoginRequest {
    @NotNull
    private String username;
    @NotNull
    private String password;
}
