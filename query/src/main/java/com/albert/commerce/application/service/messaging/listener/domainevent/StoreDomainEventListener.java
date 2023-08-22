package com.albert.commerce.application.service.messaging.listener.domainevent;

import com.albert.commerce.application.service.store.StoreFacade;
import com.albert.commerce.application.service.store.dto.StoreRegisteredEvent;
import com.albert.commerce.application.service.store.dto.StoreUploadedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreDomainEventListener {

    private final StoreFacade storeFacade;

    @KafkaListener(topics = "StoreRegisteredEvent")
    public void handleStoreRegisteredEvent(StoreRegisteredEvent storeRegisteredEvent) {
        storeFacade.register(storeRegisteredEvent);
    }

    @KafkaListener(topics = "StoreUploadedEvent")
    public void handleStoreUploadedEvent(StoreUploadedEvent storeUploadedEvent) {
        storeFacade.upload(storeUploadedEvent);
    }
}
