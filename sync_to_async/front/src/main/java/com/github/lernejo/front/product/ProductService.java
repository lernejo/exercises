package com.github.lernejo.front.product;

public interface ProductService {

    StockRemovalStatus removeFromStock(int productId, int quantity);
}
