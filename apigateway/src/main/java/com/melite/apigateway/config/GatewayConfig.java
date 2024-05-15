package com.melite.apigateway.config;

import com.melite.apigateway.filter.AuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Autowired
    AuthFilter authFilter;

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("order-service", r -> r.path("/orders")
                        .and().method("POST").filters(f -> f.filters(authFilter))
                        .uri("http://localhost:9090"))
                .route("order-service", r -> r.path("/orders/**")
                        .and().method("PUT").filters(f -> f.filters(authFilter))
                        .uri("http://localhost:9090"))

                .route("order-service", r -> r.path("/products/**")
                        .and().method("PUT").filters(f -> f.filters(authFilter))
                        .uri("http://localhost:9090"))
                .route("order-service", r -> r.path("/products")
                        .and().method("GET").filters(f -> f.filters(authFilter))
                        .uri("http://localhost:9090"))

                .route("order-service", r -> r.path("/login")
                        .and().method("POST").filters(f -> f.filters(authFilter))
                        .uri("http://localhost:9090"))
                .build();

    }

}
