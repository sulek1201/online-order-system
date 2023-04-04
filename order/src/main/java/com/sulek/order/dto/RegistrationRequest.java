package com.sulek.order.dto;

import com.sun.istack.NotNull;
import lombok.Data;


@Data
public class RegistrationRequest {
    @NotNull
    private String nameSurname;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
}
