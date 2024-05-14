package com.melita.orderservice.controller;

import com.melita.orderservice.controller.request.OrderAcceptRequest;
import com.melita.orderservice.controller.request.OrderCreateRequest;
import com.melita.orderservice.mapper.OrderMapper;
import com.melita.orderservice.model.CustomerOrder;
import com.melita.orderservice.model.dto.OrderStatusUpdateDTO;
import com.melita.orderservice.service.OrderService;
import com.melita.orderservice.BaseUnitTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest extends BaseUnitTest {

    @Mock
    OrderService orderService;

    @Mock
    OrderMapper orderMapper;

    OrderController orderController;

    @BeforeEach
    void setUp() {
        orderController = new OrderController(orderService, orderMapper);
    }

    @Test
    public void create_shouldSucceed() {
        OrderCreateRequest orderCreateRequest = getOrderCreateRequest();

        ResponseEntity<String> responseEntity = orderController.create(orderCreateRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("Your order is created.", responseEntity.getBody());
    }

    @Test
    void updateOrderStatus_shouldSucceed() {
        OrderAcceptRequest orderAcceptRequest = new OrderAcceptRequest();
        CustomerOrder order = getOrder();
        OrderStatusUpdateDTO orderStatusUpdateDTO = new OrderStatusUpdateDTO();

        when(orderMapper.map(orderAcceptRequest)).thenReturn(orderStatusUpdateDTO);

        orderController.updateOrderStatus(order.getId(), orderAcceptRequest);

        verify(orderService).sendEvent(order.getId(), orderStatusUpdateDTO);
    }


}