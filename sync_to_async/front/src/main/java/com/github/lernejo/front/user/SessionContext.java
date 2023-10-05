package com.github.lernejo.front.user;

public class SessionContext {

    private static final ThreadLocal<UserSession> store = new ThreadLocal<UserSession>();

    public static UserSession get() {
        return store.get();
    }

    public static UserSession getOrThrow() throws UserNotConnectedException {
        UserSession userSession = store.get();
        if (userSession == null) {
            throw new UserNotConnectedException();
        }
        return userSession;
    }

    static void set(UserSession session) {
        store.set(session);
    }

    static UserSession clear() {
        UserSession userSession = store.get();
        store.remove();
        return userSession;
    }
}
