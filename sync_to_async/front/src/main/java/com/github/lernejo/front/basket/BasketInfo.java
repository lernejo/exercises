package com.github.lernejo.front.basket;

import java.util.List;

record BasketInfo(int size, double price) {
    public static BasketInfo from(List<BasketProduct> basket) {
        return new BasketInfo(
            basket.stream().mapToInt(p -> p.quantity()).sum(),
            basket.stream().mapToDouble(p -> p.unitPrice() * p.quantity()).sum()
        );
    }
}
