package com.albert.commerce.shared.messaging.domain.event;

import java.util.ArrayList;
import java.util.List;

public class Events {

    private static final ThreadLocal<List<DomainEvent>> threadLocalEvents = new ThreadLocal<>();

    private Events() {
        throw new UnsupportedOperationException("Events is a utility class and should not be instantiated");
    }

    public static List<DomainEvent> getEvents() {
        if (threadLocalEvents.get() == null) {
            threadLocalEvents.set(new ArrayList<>());
        }
        return threadLocalEvents.get();
    }

    public static void raise(DomainEvent event) {
        if (event == null) {
            return;
        }
        getEvents().add(event);
    }

    public static void clear() {
        threadLocalEvents.remove();
    }

}
