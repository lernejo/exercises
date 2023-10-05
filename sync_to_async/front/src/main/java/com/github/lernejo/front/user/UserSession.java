package com.github.lernejo.front.user;

public interface UserSession {

    String getId();

    String getUsername();

    <T> T put(String name, T object);

    <T> T get(String name);
}
