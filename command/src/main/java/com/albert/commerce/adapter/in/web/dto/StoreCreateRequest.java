package com.albert.commerce.adapter.in.web.dto;

import lombok.Builder;

@Builder
public record StoreCreateRequest(
        String storeName,
        String ownerName,
        String address,
        String phoneNumber,
        String email
) {

}
