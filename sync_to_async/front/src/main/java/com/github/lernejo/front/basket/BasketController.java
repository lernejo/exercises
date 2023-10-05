package com.github.lernejo.front.basket;

import com.github.lernejo.front.user.UserNotConnectedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/basket")
record BasketController(BasketService basketService) {

    @PostMapping("/add")
    BasketInfo add(@RequestParam int productId, @RequestParam int quantity) throws ProductUnavailableException, UserNotConnectedException {
        return basketService.add(productId, quantity);
    }

    @GetMapping
    BasketInfo getBasketInfo() throws UserNotConnectedException {
        return basketService.getBasketInfo();
    }

    @GetMapping("/content")
    List<BasketProduct> getBasketContent() throws UserNotConnectedException {
        return basketService.getBasketContent();
    }

    @PostMapping("/payment")
    PaymentResult proceedToPayment(@RequestBody PaymentInformation info) throws RemoteServiceUnavailableException, UserNotConnectedException {
        return basketService.proceedToPayment(info);
    }
}
