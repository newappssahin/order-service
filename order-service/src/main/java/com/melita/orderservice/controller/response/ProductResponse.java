package com.melita.orderservice.controller.response;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String feature;
}
