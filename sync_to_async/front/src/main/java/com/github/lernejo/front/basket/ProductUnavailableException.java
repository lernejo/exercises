package com.github.lernejo.front.basket;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Product not available")
class ProductUnavailableException extends RuntimeException {
    ProductUnavailableException(String name, int quantity) {
        super("Product [" + name + "] is not available in this quantity (" + quantity + ")");
    }
}
