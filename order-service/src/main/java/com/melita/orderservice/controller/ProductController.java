package com.melita.orderservice.controller;

import com.melita.orderservice.controller.request.ProductUpdateRequest;
import com.melita.orderservice.controller.response.ProductResponse;
import com.melita.orderservice.mapper.ProductMapper;
import com.melita.orderservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @GetMapping
    public List<ProductResponse> findAllProducts() {
        return productService.findAllProduct().stream().map(productMapper::map).collect(Collectors.toList());
    }

    @PutMapping("/{productId}")
    public void updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest productUpdateRequest) {
        productService.updateProduct(productId, productUpdateRequest);
    }
}
