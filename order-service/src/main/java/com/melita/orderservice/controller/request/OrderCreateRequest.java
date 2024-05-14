package com.melita.orderservice.controller.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class OrderCreateRequest {

    @NotNull
    private String name;

    @NotNull
    private String lastName;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String phoneNumber;

    @NotNull
    private String installationAddress;

    @NotNull
    private OffsetDateTime installationDate;

    @NotNull
    private Long productId;
}
