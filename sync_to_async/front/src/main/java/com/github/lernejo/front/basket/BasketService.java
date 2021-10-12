package com.github.lernejo.front.basket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lernejo.front.product.ProductService;
import com.github.lernejo.front.product.StockRemovalStatus;
import com.github.lernejo.front.user.UserNotConnectedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.YearMonth;
import java.util.List;

@Service
final class BasketService {
    private final Logger logger = LoggerFactory.getLogger(BasketService.class);
    private final HttpClient httpClient = HttpClient.newHttpClient();

    private final ProductService productService;
    private final BasketRepository basketRepository;
    private final String paymentServiceUrl;
    private final ObjectMapper objectMapper;

    BasketService(ProductService productService,
                  BasketRepository basketRepository,
                  @Value("${service.payment.url}") String paymentServiceUrl,
                  ObjectMapper objectMapper) {
        this.productService = productService;
        this.basketRepository = basketRepository;
        this.paymentServiceUrl = paymentServiceUrl;
        this.objectMapper = objectMapper;
    }

    BasketInfo add(int productId, int quantity) throws ProductUnavailableException, UserNotConnectedException {
        StockRemovalStatus stockRemovalStatus = productService.removeFromStock(productId, quantity);
        if (stockRemovalStatus.status() == StockRemovalStatus.Status.REMOVED) {
            return basketRepository.add(new BasketProduct(stockRemovalStatus.name(), stockRemovalStatus.price(), stockRemovalStatus.quantity()));
        } else {
            throw new ProductUnavailableException(stockRemovalStatus.name(), stockRemovalStatus.quantity());
        }
    }

    BasketInfo getBasketInfo() throws UserNotConnectedException {
        List<BasketProduct> current = getBasketContent();
        return BasketInfo.from(current);
    }

    List<BasketProduct> getBasketContent() throws UserNotConnectedException {
        return basketRepository.getCurrent();
    }

    public PaymentResult proceedToPayment(PaymentInformation info) throws RemoteServiceUnavailableException, UserNotConnectedException {
        var payload = new PaymentServiceRequest(
            info.firstname() + " " + info.lastname(),
            info.cardNumber(),
            info.expirationDate(),
            info.cryptoCode(),
            getBasketInfo().price()
        );
        HttpRequest paymentRequest = buildRequest(payload);
        try {
            HttpResponse<String> response = httpClient.send(paymentRequest, HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
            return deserialize(response.body(), PaymentResult.class);
        } catch (IOException | InterruptedException e) {
            logger.error("Failed to proceed to payment, service unavailable", e);
            throw new RemoteServiceUnavailableException();
        }
    }

    private HttpRequest buildRequest(PaymentServiceRequest payload) {
        return HttpRequest.newBuilder()
            .uri(URI.create(paymentServiceUrl + "/api/payment"))
            .setHeader("Accept", "application/json")
            .setHeader("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(serialize(payload)))
            .build();
    }

    private String serialize(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    private <T> T deserialize(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

    record PaymentServiceRequest(String creditCardOwner, String cardNumber, YearMonth expirationDate, String cryptoCode,
                                 double amount) {
    }
}
