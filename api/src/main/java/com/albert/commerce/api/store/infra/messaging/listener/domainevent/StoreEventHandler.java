package com.albert.commerce.api.store.infra.messaging.listener.domainevent;

import com.albert.commerce.api.store.command.domain.StoreCreatedEvent;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class StoreEventHandler {

    @ServiceActivator(inputChannel = "com.albert.commerce.api.store.command.domain.StoreCreatedEvent")
    public void handleStoreCreateEvent(StoreCreatedEvent storeCreatedEvent) {
        System.err.println(storeCreatedEvent);
    }

}
