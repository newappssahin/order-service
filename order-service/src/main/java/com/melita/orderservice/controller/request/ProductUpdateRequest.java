package com.melita.orderservice.controller.request;

import lombok.Data;

@Data
public class ProductUpdateRequest {
    private String name;

    private String feature;
}
