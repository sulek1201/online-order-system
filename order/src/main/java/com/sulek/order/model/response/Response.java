package com.sulek.order.model.response;

import com.google.gson.JsonObject;
import lombok.Data;

@Data
public class Response {

    private ErrorResponse errors;
    private JsonObject data;
}
