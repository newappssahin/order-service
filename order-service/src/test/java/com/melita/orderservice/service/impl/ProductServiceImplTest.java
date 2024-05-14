package com.melita.orderservice.service.impl;

import com.melita.orderservice.BaseUnitTest;
import com.melita.orderservice.model.Product;
import com.melita.orderservice.repository.ProductRepository;
import com.melita.orderservice.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest extends BaseUnitTest {

    @Mock
    ProductRepository productRepository;

    ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductServiceImpl(productRepository);
    }

    @Test
    public void findAllProduct_shouldSucceed() {
        Product product = getProduct();

        when(productService.findAllProduct()).thenReturn(List.of(product));

        List<Product> allProduct = productService.findAllProduct();

        assertEquals(product, allProduct.stream().findAny().get());
    }

    @Test
    void getById_shouldSucceed() {
        Product product = getProduct();

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));

        Product byId = productService.getById(product.getId());

        assertEquals(product, byId);
    }

    @Test
    void getById_shouldThrowException_whenProductNotFound() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> productService.getById(anyLong()));

        assertEquals("Product not found", exception.getMessage());
    }
}