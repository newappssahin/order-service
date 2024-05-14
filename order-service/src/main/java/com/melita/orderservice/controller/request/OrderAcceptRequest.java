package com.melita.orderservice.controller.request;

import com.melita.orderservice.model.Status;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderAcceptRequest {

    @NotNull
    private Status status;

    private String rejectionReason;
}
