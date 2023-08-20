package com.albert.commerce.application.service.store;

import com.albert.commerce.application.service.store.dto.StoreRegisteredEvent;
import com.albert.commerce.application.service.store.dto.StoreUploadedEvent;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreDao;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.exception.StoreNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class StoreFacade {

    private final StoreDao storeDao;

    @Transactional(readOnly = true)
    public Store getStoreById(StoreId storeId) {
        return storeDao.findById(storeId).orElseThrow(StoreNotFoundException::new);
    }

    @Transactional
    public void register(StoreRegisteredEvent storeRegisteredEvent) {
        var store = toStore(storeRegisteredEvent);
        storeDao.save(store);
    }

    @Transactional
    public void upload(StoreUploadedEvent storeUploadedEvent) {
        var store = getStoreById(storeUploadedEvent.storeId());
        upload(storeUploadedEvent, store);
    }

    private static void upload(StoreUploadedEvent storeUploadedEvent, Store store) {
        store.update(
                storeUploadedEvent.storeName(),
                storeUploadedEvent.ownerName(),
                storeUploadedEvent.address(),
                storeUploadedEvent.phoneNumber(),
                storeUploadedEvent.email()
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
                .build();
    }
}
