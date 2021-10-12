package com.github.lernejo.front.user;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Repository
class InMemorySessionRepository implements SessionRepository {

    private final Map<String, UserSession> sessionsById = new HashMap<>();

    @Override
    public UserSession create(String username) {
        String sessionId;
        do {
            sessionId = UUID.randomUUID().toString();
        } while (sessionsById.containsKey(sessionId));
        InternalUserSession session = new InternalUserSession(sessionId, username);
        sessionsById.put(sessionId, session);
        return session;
    }

    @Override
    public UserSession get(String sessionId) {
        return sessionsById.get(sessionId);
    }

    @Override
    public void destroy(String sessionId) {
        sessionsById.remove(sessionId);
    }
}
