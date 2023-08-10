package com.albert.commerce.api.store.infra.messaging.listener.domainevent;

import com.albert.commerce.api.store.command.domain.StoreCreatedEvent;
import com.albert.commerce.api.store.command.domain.StoreUpdateEvent;
import com.albert.commerce.api.store.query.application.StoreFacade;
import com.albert.commerce.api.store.query.application.dto.UpdateStoreRequest;
import com.albert.commerce.api.store.query.domain.StoreData;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreEventHandler {

    private final StoreFacade storeFacade;

    @ServiceActivator(inputChannel = "com.albert.commerce.api.store.command.domain.StoreCreatedEvent")
    public void handleStoreCreateEvent(StoreCreatedEvent storeCreatedEvent) {
        StoreData storeData = StoreData.builder()
                .storeId(storeCreatedEvent.getStoreId())
                .storeName(storeCreatedEvent.getStoreName())
                .userId(storeCreatedEvent.getUserId())
                .ownerName(storeCreatedEvent.getOwnerName())
                .address(storeCreatedEvent.getAddress())
                .phoneNumber(storeCreatedEvent.getPhoneNumber())
                .email(storeCreatedEvent.getEmail())
                .build();
        storeFacade.save(storeData);
    }

    @ServiceActivator(inputChannel = "com.albert.commerce.api.store.command.domain.StoreUpdateEvent")
    public void handleStoreUpdateEvent(StoreUpdateEvent storeUpdateEvent) {
        UpdateStoreRequest updateStoreRequest = UpdateStoreRequest.builder()
                .storeId(storeUpdateEvent.getStoreId())
                .address(storeUpdateEvent.getAddress())
                .phoneNumber(storeUpdateEvent.getPhoneNumber())
                .storeName(storeUpdateEvent.getStoreName())
                .ownerName(storeUpdateEvent.getOwnerName())
                .email(storeUpdateEvent.getEmail())
                .build();
        storeFacade.update(updateStoreRequest);
    }
}
