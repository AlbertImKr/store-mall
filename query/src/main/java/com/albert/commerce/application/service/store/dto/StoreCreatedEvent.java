package com.albert.commerce.application.service.store.dto;

import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;

public record StoreCreatedEvent(
        StoreId storeId,
        String storeName,
        UserId userId,
        String ownerName,
        String address,
        String phoneNumber,
        String email
) {

}
