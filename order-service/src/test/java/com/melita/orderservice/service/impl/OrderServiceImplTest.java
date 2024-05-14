package com.melita.orderservice.service.impl;

import com.melita.orderservice.BaseUnitTest;
import com.melita.orderservice.mapper.OrderMapper;
import com.melita.orderservice.model.CustomerOrder;
import com.melita.orderservice.model.Product;
import com.melita.orderservice.model.Status;
import com.melita.orderservice.model.dto.OrderEventDTO;
import com.melita.orderservice.model.dto.OrderStatusUpdateDTO;
import com.melita.orderservice.queue.ProducerService;
import com.melita.orderservice.repository.CustomerOrderRepository;
import com.melita.orderservice.service.OrderService;
import com.melita.orderservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest extends BaseUnitTest {

    @Mock
    CustomerOrderRepository customerOrderRepository;

    @Mock
    ProductService productService;

    @Mock
    ProducerService producerService;

    @Mock
    OrderMapper orderMapper;

    OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl(customerOrderRepository, productService, producerService, orderMapper);
    }

    @Test
    public void create_shouldSucceed() {
        //arrange
        CustomerOrder order = getOrder();
        Product product = getProduct();

        //when
        when(productService.getById(product.getId())).thenReturn(product);

        //act
        orderService.create(product.getId(), order);

        //assertion
        verify(customerOrderRepository).save(order);
        assertEquals(product, order.getProduct());
    }

    @Test
    public void create_shouldThrowException_whenProductNotFound() {
        Product product = getProduct();

        when(productService.getById(product.getId())).thenThrow(new RuntimeException("Product not found"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.create(product.getId(), getOrder()));
        assertEquals("Product not found", exception.getMessage());

    }

    @Test
    public void updateStatus_shouldSucceed_whenStatusCompleted() {
        CustomerOrder order = getOrder();
        OrderEventDTO orderEvent = getOrderEvent();

        orderService.updateStatus(order, orderEvent);

        verify(customerOrderRepository).save(order);
        assertEquals(Status.COMPLETED, order.getStatus());
        assertNull(order.getRejectionReason());
    }

    @Test
    public void updateStatus_shouldSetRejectionReason_whenHas() {
        String rejectReason = "Unsuitable address";
        CustomerOrder order = getOrder();
        OrderEventDTO orderEvent = getOrderEvent();
        orderEvent.setStatus(Status.REJECTED);
        orderEvent.setRejectReason(rejectReason);

        orderService.updateStatus(order, orderEvent);

        verify(customerOrderRepository).save(order);
        assertEquals(Status.REJECTED, order.getStatus());
        assertEquals(rejectReason, order.getRejectionReason());
    }

    //TODO write test for sendEmail


    @Test
    public void getOrderById_shouldSucceed() {
        CustomerOrder order = getOrder();

        when(customerOrderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        CustomerOrder customerOrder = orderService.getOrderById(order.getId());

        assertEquals(order, customerOrder);
    }

    @Test
    public void getOrderById_shouldThrowException_whenOrderNotFound() {
        CustomerOrder order = getOrder();

        when(customerOrderRepository.findById(order.getId())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> orderService.getOrderById(order.getId()));

        assertEquals("Order not found", exception.getMessage());
    }

    @Test
    public void sendEvent_shouldSucceed_whenOrderStatusUpdateIsCompleted() {
        CustomerOrder order = getOrder();
        OrderStatusUpdateDTO orderStatusUpdateDTO = getOrderStatusUpdateDTO();
        OrderEventDTO orderEvent = getOrderEvent();

        when(customerOrderRepository.findById(order.getId())).thenReturn(Optional.of(order));
        when(orderMapper.mapToOrderEvent(order.getId(), orderStatusUpdateDTO)).thenReturn(orderEvent);

        orderService.sendEvent(order.getId(), orderStatusUpdateDTO);

        verify(producerService).publishMessage(orderEvent);
    }

    @Test
    public void sendEvent_shouldThrowException_whenOrderAndOrderStatusUpdateStatusSame() {
        CustomerOrder order = getOrder();
        OrderStatusUpdateDTO orderStatusUpdateDTO = getOrderStatusUpdateDTO();
        orderStatusUpdateDTO.setStatus(order.getStatus());

        when(customerOrderRepository.findById(order.getId())).thenReturn(Optional.of(order));

        Exception exception = assertThrows(Exception.class, () -> orderService.sendEvent(order.getId(), orderStatusUpdateDTO));

        assertEquals(String.format("Order id: %s already has this status %s ", order.getId(), orderStatusUpdateDTO.getStatus()), exception.getMessage());
        verifyNoInteractions(orderMapper);
        verifyNoInteractions(producerService);
    }

}