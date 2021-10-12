package com.github.lernejo.front.basket;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.SERVICE_UNAVAILABLE, reason = "Remote payment service unavailable")
public class RemoteServiceUnavailableException extends RuntimeException {
}
