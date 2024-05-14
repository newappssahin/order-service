package com.melita.orderservice.controller.response;

import com.melita.orderservice.model.Status;
import lombok.Data;

@Data
public class OrderCreateResponse {
    private Status status;
}
