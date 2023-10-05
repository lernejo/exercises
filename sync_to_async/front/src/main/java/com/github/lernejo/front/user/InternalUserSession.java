package com.github.lernejo.front.user;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
class InternalUserSession implements UserSession {

    private final String id;
    private final String username;
    private final Map<String, Object> store = new HashMap<>();

    InternalUserSession(String id, String username) {
        this.id = id;
        this.username = username;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override

    public <T> T put(String name, T object) {
        return (T) store.put(name, object);
    }

    @Override
    public <T> T get(String name) {
        return (T) store.get(name);
    }
}
