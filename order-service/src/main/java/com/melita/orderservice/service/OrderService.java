package com.melita.orderservice.service;

import com.melita.orderservice.model.CustomerOrder;
import com.melita.orderservice.model.dto.OrderEventDTO;
import com.melita.orderservice.model.dto.OrderStatusUpdateDTO;

public interface OrderService {
    void create(Long productId, CustomerOrder customerOrder);

    CustomerOrder updateStatus(CustomerOrder order, OrderEventDTO orderEventDTO);

    CustomerOrder getOrderById(Long orderId);

    void sendEvent(Long orderId, OrderStatusUpdateDTO map);
}
