package com.albert.commerce.store.command.application;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreUserId;
import lombok.Builder;

@Builder
public record NewStoreRequest(String storeName, String ownerName, String address,
                              String phoneNumber,

                              String email) {

    public Store toStore(StoreUserId storeUserId, StoreId storeId) {
        return Store.builder()
                .storeUserId(storeUserId)
                .storeId(storeId)
                .storeName(storeName)
                .ownerName(ownerName)
                .address(address)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
    }
}
