package com.albert.commerce.api.store.query.application.dto;

import com.albert.commerce.api.store.command.domain.StoreId;
import lombok.Builder;

@Builder
public record UpdateStoreRequest(
        StoreId storeId,
        String storeName,
        String address,
        String phoneNumber,
        String email,
        String ownerName) {

}
