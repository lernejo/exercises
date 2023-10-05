package com.github.lernejo.front.product;

import java.util.Objects;

public record Product(int id,
                      String name,
                      double price,
                      // A negative quantity means inf.
                      int quantity) {
    public Product remove(int quantity) {
        if (quantity > this.quantity) {
            throw new IllegalArgumentException("Not enough available products");
        }
        return new Product(id, name, price, this.quantity - quantity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
