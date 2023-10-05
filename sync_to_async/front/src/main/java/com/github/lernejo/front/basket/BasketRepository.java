package com.github.lernejo.front.basket;

import com.github.lernejo.front.user.SessionContext;
import com.github.lernejo.front.user.UserNotConnectedException;
import com.github.lernejo.front.user.UserSession;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
class BasketRepository {

    private static final String BASKET_SESSION_KEY = "basket";

    BasketInfo add(BasketProduct basketProduct) throws UserNotConnectedException {
        List<BasketProduct> basket = getBasket();
        basket.add(basketProduct);
        return BasketInfo.from(basket);
    }

    public List<BasketProduct> getCurrent() throws UserNotConnectedException {
        return getBasket();
    }

    private List<BasketProduct> getBasket() throws UserNotConnectedException {
        UserSession userSession = SessionContext.getOrThrow();
        List<BasketProduct> basket = userSession.get(BASKET_SESSION_KEY);
        if (basket == null) {
            basket = new ArrayList<>();
            userSession.put(BASKET_SESSION_KEY, basket);
        }
        return basket;
    }
}
