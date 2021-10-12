package com.github.lernejo.front.basket;

import java.time.YearMonth;

public record PaymentInformation(String lastname, String firstname, String cardNumber, YearMonth expirationDate,
                                 String cryptoCode) {
}
