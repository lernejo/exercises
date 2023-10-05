package com.github.lernejo.front.user;

interface SessionRepository {

    UserSession create(String username);

    UserSession get(String sessionId);

    void destroy(String sessionId);
}
