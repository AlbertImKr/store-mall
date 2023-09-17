package com.albert.commerce.adapter.in.web.security;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Component;

@Component
public class SessionStorage {

    private final Map<String, String> storage = new HashMap<>();

    public void save(String sessionId, String data) {
        storage.put(sessionId, data);
    }

    public String retrieve(String sessionId) {
        return storage.get(sessionId);
    }

    public void delete(String sessionId) {
        storage.remove(sessionId);
    }
}
