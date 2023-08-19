package com.albert.commerce.application.service.messaging.listener.domainevent;

import com.albert.commerce.application.service.store.StoreFacade;
import com.albert.commerce.application.service.store.dto.StoreCreatedEvent;
import com.albert.commerce.application.service.store.dto.StoreUpdatedEvent;
import com.albert.commerce.domain.store.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreEventHandler {

    private final StoreFacade storeFacade;

    @KafkaListener(topics = "StoreCreatedEvent")
    public void handleStoreCreateEvent(StoreCreatedEvent storeCreatedEvent) {
        Store store = Store.builder()
                .storeId(storeCreatedEvent.storeId())
                .storeName(storeCreatedEvent.storeName())
                .userId(storeCreatedEvent.userId())
                .ownerName(storeCreatedEvent.ownerName())
                .address(storeCreatedEvent.address())
                .phoneNumber(storeCreatedEvent.phoneNumber())
                .email(storeCreatedEvent.email())
                .build();
        storeFacade.save(store);
    }

    @KafkaListener(topics = "StoreUpdateEvent")
    public void handleStoreUpdateEvent(StoreUpdatedEvent storeUpdatedEvent) {
        storeFacade.update(storeUpdatedEvent);
    }
}
