package com.github.lernejo.front.product;

public record StockRemovalStatus(Status status, String name, double price, int quantity) {

    public enum Status {
        REMOVED,
        NOT_AVAILABLE,
    }
}
