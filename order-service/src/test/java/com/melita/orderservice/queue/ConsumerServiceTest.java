package com.melita.orderservice.queue;

import com.melita.orderservice.BaseUnitTest;
import com.melita.orderservice.model.CustomerOrder;
import com.melita.orderservice.model.Status;
import com.melita.orderservice.model.dto.OrderEventDTO;
import com.melita.orderservice.service.JavaSmtpGmailSenderService;
import com.melita.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConsumerServiceTest extends BaseUnitTest {

    @Mock
    OrderService orderService;

    @Mock
    JavaSmtpGmailSenderService gmailSenderService;

    ConsumerService consumerService;

    @BeforeEach
    void setUp() {
        consumerService = new ConsumerService(orderService, gmailSenderService);
    }

    @Test
    void listen_shouldSucceed(){
        OrderEventDTO orderEvent = getOrderEvent();
        CustomerOrder order = getOrder();
        CustomerOrder customerOrder = getOrder();
        customerOrder.setStatus(Status.COMPLETED);

        when(orderService.getOrderById(orderEvent.getOrderId())).thenReturn(order);
        when(orderService.updateStatus(order, orderEvent)).thenReturn(customerOrder);

        consumerService.listen(orderEvent);
    }

    @Test
    void listen_shouldThrowException_whenEventAndOrderStatusesAreEqual(){
        OrderEventDTO orderEvent = getOrderEvent();
        CustomerOrder order = getOrder();
        order.setStatus(orderEvent.getStatus());

        when(orderService.getOrderById(orderEvent.getOrderId())).thenReturn(order);

        Exception exception = assertThrows(Exception.class, () -> consumerService.listen(orderEvent));

        assertEquals(String.format("Order status already has this status: %s ", orderEvent.getStatus()), exception.getMessage());
    }

}