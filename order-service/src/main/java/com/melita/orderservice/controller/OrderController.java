package com.melita.orderservice.controller;

import com.melita.orderservice.controller.request.OrderAcceptRequest;
import com.melita.orderservice.controller.request.OrderCreateRequest;
import com.melita.orderservice.mapper.OrderMapper;
import com.melita.orderservice.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid OrderCreateRequest orderCreateRequest) {
        log.info("Order is created: {}", orderCreateRequest);
        orderService.create(orderCreateRequest.getProductId(), orderMapper.mapToEntity(orderCreateRequest));
        return new ResponseEntity<>("Your order is created.", HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public void updateOrderStatus(@PathVariable Long orderId, @RequestBody @Valid OrderAcceptRequest orderAcceptRequest) {
        log.info("Order status update request: {}", orderAcceptRequest);
        orderService.sendEvent(orderId, orderMapper.map(orderAcceptRequest));
    }

}
