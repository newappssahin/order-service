package com.melita.orderservice.mapper;

import com.melita.orderservice.controller.request.OrderAcceptRequest;
import com.melita.orderservice.controller.request.OrderCreateRequest;
import com.melita.orderservice.model.CustomerOrder;
import com.melita.orderservice.model.dto.OrderEventDTO;
import com.melita.orderservice.model.dto.OrderStatusUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    CustomerOrder mapToEntity(OrderCreateRequest orderCreateRequest);

    OrderStatusUpdateDTO map(OrderAcceptRequest orderAcceptRequest);

    @Mapping(target = "orderId", source = "orderId")
    OrderEventDTO mapToOrderEvent(Long orderId, OrderStatusUpdateDTO order);
}
