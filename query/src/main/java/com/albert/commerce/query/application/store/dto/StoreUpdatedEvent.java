package com.albert.commerce.query.application.store.dto;

import com.albert.commerce.query.domain.store.StoreId;
import lombok.Builder;

@Builder
public record StoreUpdatedEvent(
        StoreId storeId,
        String storeName,
        String address,
        String phoneNumber,
        String email,
        String ownerName) {

}
