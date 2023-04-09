package com.sulek.seller.dto;

import com.sun.istack.NotNull;
import lombok.Data;


@Data
public class RegistrationRequest {
    @NotNull
    private String businessName;
    @NotNull
    private String password;
    @NotNull
    private String email;
    @NotNull
    private String address;
}
