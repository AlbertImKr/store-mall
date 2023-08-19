package com.albert.commerce.application.service.store.dto;

import com.albert.commerce.domain.store.StoreId;
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
