package com.github.lernejo.front.basket;

public record PaymentResult(PaymentStatus status, String bankName) {
    public enum PaymentStatus {
        OK,
        TRANSMITTED,
        KO,
        CANCELLED,
    }
}
