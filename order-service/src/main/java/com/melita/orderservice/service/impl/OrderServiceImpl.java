package com.melita.orderservice.service.impl;

import com.melita.orderservice.mapper.OrderMapper;
import com.melita.orderservice.model.CustomerOrder;
import com.melita.orderservice.model.Product;
import com.melita.orderservice.model.dto.OrderEventDTO;
import com.melita.orderservice.model.dto.OrderStatusUpdateDTO;
import com.melita.orderservice.queue.ProducerService;
import com.melita.orderservice.repository.CustomerOrderRepository;
import com.melita.orderservice.service.OrderService;
import com.melita.orderservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);
    private final CustomerOrderRepository customerOrderRepository;
    private final ProductService productService;
    private final ProducerService producerService;
    private final OrderMapper orderMapper;

    @Override
    public void create(Long productId, CustomerOrder customerOrder) {
        Product product = productService.getById(productId);
        customerOrder.setProduct(product);
        customerOrderRepository.save(customerOrder);
    }

    @SneakyThrows
    @Override
    public CustomerOrder updateStatus(CustomerOrder customerOrder, OrderEventDTO orderDTO) {
        customerOrder.setStatus(orderDTO.getStatus());
        if (Objects.nonNull(orderDTO.getRejectReason())) {
            customerOrder.setRejectionReason(orderDTO.getRejectReason());
        }

        return customerOrderRepository.save(customerOrder);
    }

    @Override
    public CustomerOrder getOrderById(Long orderId) {
        return customerOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @SneakyThrows
    @Override
    public void sendEvent(Long orderId, OrderStatusUpdateDTO orderDTO) {
        CustomerOrder customerOrder = customerOrderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (Objects.equals(customerOrder.getStatus(), orderDTO.getStatus())) {
            throw new Exception(String.format("Order id: %s already has this status %s ", orderId, orderDTO.getStatus()));
        }
        producerService.publishMessage(orderMapper.mapToOrderEvent(orderId, orderDTO));
    }
}
