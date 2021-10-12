package com.github.lernejo.front.product;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Repository
class InMemoryDemoProductRepository implements ProductRepository {

    private final Set<Product> products = ConcurrentHashMap.newKeySet();

    InMemoryDemoProductRepository() {
        products.add(new Product(1, "Tomato", 0.73D, 200));
        products.add(new Product(2, "TV", 1299.99D, 50));
        products.add(new Product(3, "PS5", 600D, 0));
        products.add(new Product(4, "Intel Core i5-11600K 3,9 GHz 12 Mo", 265.11D, -1));
    }

    @Override
    public List<Product> listProducts() {
        return List.copyOf(products);
    }

    @Override
    public boolean removeFromStock(int productId, int quantity) {
        Product product = getProduct(productId);
        if (product.quantity() < 0) {
            return true;
        } else if (product.quantity() >= quantity) {
            products.remove(product);
            products.add(product.remove(quantity));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Product getProduct(int productId) {
        return products
            .stream()
            .filter(p -> p.id() == productId)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("No product with ID: " + productId));
    }
}
