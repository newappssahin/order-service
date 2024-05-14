package com.melita.orderservice.mapper;


import com.melita.orderservice.BaseUnitTest;
import com.melita.orderservice.controller.request.OrderAcceptRequest;
import com.melita.orderservice.controller.request.OrderCreateRequest;
import com.melita.orderservice.model.CustomerOrder;
import com.melita.orderservice.model.Status;
import com.melita.orderservice.model.dto.OrderEventDTO;
import com.melita.orderservice.model.dto.OrderStatusUpdateDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderMapperTest extends BaseUnitTest {
    OrderMapper orderMapper = Mappers.getMapper(OrderMapper.class);

    @Test
    void mapToEntity_OrderCreateRequestToCustomerOrder() {
        OrderCreateRequest orderCreateRequest = getOrderCreateRequest();

        CustomerOrder result = orderMapper.mapToEntity(orderCreateRequest);

        assertEquals(orderCreateRequest.getName(), result.getName());
        assertEquals(orderCreateRequest.getEmail(), result.getEmail());
        assertEquals(orderCreateRequest.getPhoneNumber(), result.getPhoneNumber());
        assertEquals(orderCreateRequest.getLastName(), result.getLastName());
        assertEquals(orderCreateRequest.getInstallationAddress(), result.getInstallationAddress());
        assertEquals(orderCreateRequest.getInstallationDate(), result.getInstallationDate());
    }

    @Test
    void map_OrderAcceptRequestToOrderStatusUpdateDTO() {
        OrderAcceptRequest orderAcceptRequest = new OrderAcceptRequest();
        orderAcceptRequest.setStatus(Status.REJECTED);
        orderAcceptRequest.setRejectionReason("sorry");

        OrderStatusUpdateDTO result = orderMapper.map(orderAcceptRequest);

        assertEquals(orderAcceptRequest.getStatus(), result.getStatus());
        assertEquals(orderAcceptRequest.getRejectionReason(), result.getRejectionReason());
    }

    @Test
    void mapToOrderEvent_orderIdAndOrderStatusUpdateDTOToOrderEventDTO() {
        OrderStatusUpdateDTO orderStatusUpdateDTO = getOrderStatusUpdateDTO();
        Long orderId = 1L;

        OrderEventDTO result = orderMapper.mapToOrderEvent(orderId, orderStatusUpdateDTO);

        assertEquals(orderStatusUpdateDTO.getStatus(), result.getStatus());
        assertEquals(orderStatusUpdateDTO.getRejectionReason(), result.getRejectReason());
        assertEquals(orderId, result.getOrderId());
    }

}