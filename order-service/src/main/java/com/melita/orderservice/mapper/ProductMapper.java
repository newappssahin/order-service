package com.melita.orderservice.mapper;

import com.melita.orderservice.controller.response.ProductResponse;
import com.melita.orderservice.model.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductResponse map(Product product);
}
