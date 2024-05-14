package com.melita.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public enum ProductCategory {
    TV(List.of("90 Channels", "140 Channels")),
    INTERNET(List.of("250Mbps", "1Gbps")),
    TELEPHONY(List.of("Free On net Calls", "Unlimited Calls")),
    MOBILE(List.of("Prepaid", " Post-paid"));

    private final List<String> feature;
}
