package com.melita.orderservice.service;

import com.melita.orderservice.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProduct();

    Product getById(Long id);
}
