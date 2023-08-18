package com.albert.commerce.query.infra.messaging.listener.domainevent;

import com.albert.commerce.query.application.store.StoreFacade;
import com.albert.commerce.query.application.store.dto.StoreCreatedEvent;
import com.albert.commerce.query.application.store.dto.StoreUpdatedEvent;
import com.albert.commerce.query.domain.store.StoreData;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreEventHandler {

    private final StoreFacade storeFacade;

    @KafkaListener(topics = "StoreCreatedEvent")
    public void handleStoreCreateEvent(StoreCreatedEvent storeCreatedEvent) {
        StoreData storeData = StoreData.builder()
                .storeId(storeCreatedEvent.storeId())
                .storeName(storeCreatedEvent.storeName())
                .userId(storeCreatedEvent.userId())
                .ownerName(storeCreatedEvent.ownerName())
                .address(storeCreatedEvent.address())
                .phoneNumber(storeCreatedEvent.phoneNumber())
                .email(storeCreatedEvent.email())
                .build();
        storeFacade.save(storeData);
    }

    @KafkaListener(topics = "StoreUpdateEvent")
    public void handleStoreUpdateEvent(StoreUpdatedEvent storeUpdatedEvent) {
        storeFacade.update(storeUpdatedEvent);
    }
}
