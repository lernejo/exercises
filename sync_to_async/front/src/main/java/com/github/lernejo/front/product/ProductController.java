package com.github.lernejo.front.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/product")
record ProductController(ProductRepository productRepository) {

    @GetMapping("")
    List<Product> listProducts() {
        return productRepository.listProducts();
    }
}
