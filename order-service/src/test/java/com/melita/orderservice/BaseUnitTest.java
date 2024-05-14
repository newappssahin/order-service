package com.melita.orderservice;

import com.melita.orderservice.controller.request.OrderCreateRequest;
import com.melita.orderservice.model.CustomerOrder;
import com.melita.orderservice.model.Product;
import com.melita.orderservice.model.ProductCategory;
import com.melita.orderservice.model.Status;
import com.melita.orderservice.model.dto.OrderEventDTO;
import com.melita.orderservice.model.dto.OrderStatusUpdateDTO;

import java.time.OffsetDateTime;

public abstract class BaseUnitTest {
    public OrderStatusUpdateDTO getOrderStatusUpdateDTO() {
        OrderStatusUpdateDTO orderStatusUpdateDTO = new OrderStatusUpdateDTO();
        orderStatusUpdateDTO.setStatus(Status.COMPLETED);
        return orderStatusUpdateDTO;
    }


    public OrderEventDTO getOrderEvent() {
        OrderEventDTO orderEventDTO = new OrderEventDTO();
        orderEventDTO.setOrderId(getOrder().getId());
        orderEventDTO.setStatus(Status.COMPLETED);
        return orderEventDTO;
    }

    public CustomerOrder getOrder() {
        CustomerOrder order = new CustomerOrder();
        order.setProduct(getProduct());
        order.setName("Joe's Order");
        order.setStatus(Status.INITIATED);
        order.setEmail("joe@mail.com");
        return order;
    }

    public Product getProduct() {
        ProductCategory tv = ProductCategory.TV;
        Product product = new Product();
        product.setId(1l);
        product.setName(tv.name());
        product.setFeature(tv.getFeature().stream().findAny().get());
        return product;
    }

    public OrderCreateRequest getOrderCreateRequest() {
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest();
        orderCreateRequest.setEmail("joe@mail.com");
        orderCreateRequest.setPhoneNumber("123456789");
        orderCreateRequest.setName("joe");
        orderCreateRequest.setLastName("doe");
        orderCreateRequest.setInstallationAddress("street 1");
        orderCreateRequest.setInstallationDate(OffsetDateTime.now());
        orderCreateRequest.setProductId(1l);
        return orderCreateRequest;
    }
}
