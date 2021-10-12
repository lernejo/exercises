package com.github.lernejo.front.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "UserNotConnected")
public class UserNotConnectedException extends RuntimeException {
}
