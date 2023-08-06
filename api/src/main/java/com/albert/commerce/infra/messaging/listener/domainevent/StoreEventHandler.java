package com.albert.commerce.infra.messaging.listener.domainevent;

import com.albert.commerce.domain.command.store.StoreCreatedEvent;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@Service
public class StoreEventHandler {


    @ServiceActivator(inputChannel = "com.albert.commerce.domain.command.store.StoreCreatedEvent")
    public void handleStoreCreatedEvent(StoreCreatedEvent storeCreatedEvent) {
        System.err.println(storeCreatedEvent.getClass().getName());
    }

}
