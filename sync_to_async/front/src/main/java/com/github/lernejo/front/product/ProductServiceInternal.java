package com.github.lernejo.front.product;

import org.springframework.stereotype.Service;

@Service
record ProductServiceInternal(ProductRepository productRepository) implements ProductService {

    @Override
    public StockRemovalStatus removeFromStock(int productId, int quantity) {
        boolean success = productRepository.removeFromStock(productId, quantity);
        Product product = productRepository.getProduct(productId);
        return new StockRemovalStatus(
            success ? StockRemovalStatus.Status.REMOVED : StockRemovalStatus.Status.NOT_AVAILABLE,
            product.name(),
            product.price(),
            quantity);
    }
}
