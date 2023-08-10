package com.albert.commerce.api.store.query.application.dto;

import com.albert.commerce.common.domain.DomainId;
import lombok.Builder;

@Builder
public record UpdateStoreRequest(
        DomainId storeId,
        String storeName,
        String address,
        String phoneNumber,
        String email,
        String ownerName) {

}
