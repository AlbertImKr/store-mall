package com.albert.commerce.api.store.infra.messaging.listener.domainevent;

import com.albert.commerce.api.store.command.domain.StoreCreatedEvent;
import com.albert.commerce.api.store.query.domain.StoreData;
import com.albert.commerce.api.store.query.domain.StoreDataDao;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreEventHandler {

    private final StoreDataDao storeDataDao;

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
        storeDataDao.save(storeData);
    }
}
