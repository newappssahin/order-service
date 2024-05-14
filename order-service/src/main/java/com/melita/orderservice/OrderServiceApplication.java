package com.melita.orderservice;

import com.melita.orderservice.model.Product;
import com.melita.orderservice.model.ProductCategory;
import com.melita.orderservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@RequiredArgsConstructor
@EnableDiscoveryClient
public class OrderServiceApplication implements CommandLineRunner {
    private final ProductRepository productRepository;

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        List<Product> products = Arrays.stream(ProductCategory.values())
                .flatMap(productCategory -> productCategory.getFeature().stream()
                        .map(feature -> {
                            Product product = new Product();
                            product.setName(productCategory.name());
                            product.setFeature(feature);
                            return product;
                        }))
                .collect(Collectors.toList());

        productRepository.saveAll(products);
    }

}
