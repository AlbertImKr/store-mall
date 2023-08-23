package com.albert.commerce.adapter.in.web.request;

import lombok.Builder;

@Builder
public record StoreRegisterRequest(
        String storeName,
        String ownerName,
        String address,
        String phoneNumber,
        String email
) {

}
