package com.melita.orderservice.service;

import com.melita.orderservice.controller.request.ProductUpdateRequest;
import com.melita.orderservice.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAllProduct();

    Product getById(Long id);

    void updateProduct(Long productId, ProductUpdateRequest productUpdateRequest);
}
