package com.albert.commerce.query.application.store.dto;

import com.albert.commerce.query.domain.store.StoreId;
import com.albert.commerce.query.domain.user.UserId;

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
