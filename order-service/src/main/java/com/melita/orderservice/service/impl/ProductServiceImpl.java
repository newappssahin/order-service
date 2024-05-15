package com.melita.orderservice.service.impl;

import com.melita.orderservice.controller.request.ProductUpdateRequest;
import com.melita.orderservice.model.Product;
import com.melita.orderservice.repository.ProductRepository;
import com.melita.orderservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public void updateProduct(Long productId, ProductUpdateRequest productUpdateRequest) {
        Product product = getById(productId);

        if (Objects.nonNull(productUpdateRequest.getName())) {
            product.setName(productUpdateRequest.getName());
        }
        if (Objects.nonNull(productUpdateRequest.getFeature())) {
            product.setFeature(productUpdateRequest.getFeature());
        }

        productRepository.save(product);
    }
}
