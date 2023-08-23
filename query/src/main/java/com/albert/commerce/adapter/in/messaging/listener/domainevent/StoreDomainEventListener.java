package com.albert.commerce.adapter.in.messaging.listener.domainevent;

import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.StoreRegisteredEvent;
import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.StoreUploadedEvent;
import com.albert.commerce.adapter.out.persistance.imports.StoreJpaRepository;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.exception.error.StoreNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreDomainEventListener {

    private final StoreJpaRepository storeJpaRepository;

    @KafkaListener(topics = "StoreRegisteredEvent")
    public void handleStoreRegisteredEvent(StoreRegisteredEvent storeRegisteredEvent) {
        var store = toStore(storeRegisteredEvent);
        storeJpaRepository.save(store);
    }

    @KafkaListener(topics = "StoreUploadedEvent")
    public void handleStoreUploadedEvent(StoreUploadedEvent storeUploadedEvent) {
        var store = storeJpaRepository.findById((storeUploadedEvent.storeId()))
                .orElseThrow(StoreNotFoundException::new);
        upload(storeUploadedEvent, store);
    }

    private static void upload(StoreUploadedEvent storeUploadedEvent, Store store) {
        store.update(
                storeUploadedEvent.storeName(),
                storeUploadedEvent.ownerName(),
                storeUploadedEvent.address(),
                storeUploadedEvent.phoneNumber(),
                storeUploadedEvent.email(),
                storeUploadedEvent.updatedTime()
        );
    }

    private static Store toStore(StoreRegisteredEvent storeRegisteredEvent) {
        return Store.builder()
                .storeId(storeRegisteredEvent.storeId())
                .storeName(storeRegisteredEvent.storeName())
                .userId(storeRegisteredEvent.userId())
                .ownerName(storeRegisteredEvent.ownerName())
                .address(storeRegisteredEvent.address())
                .phoneNumber(storeRegisteredEvent.phoneNumber())
                .email(storeRegisteredEvent.email())
                .createdTime(storeRegisteredEvent.createdTime())
                .updatedTime(storeRegisteredEvent.updatedTime())
                .build();
    }
}
