package com.github.lernejo.front.product;

import java.util.List;

interface ProductRepository {
    List<Product> listProducts();

    boolean removeFromStock(int productId, int quantity);

    Product getProduct(int productId);
}
