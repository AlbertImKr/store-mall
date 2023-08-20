package com.albert.commerce.adapter.in.web.dto;

import lombok.Builder;

@Builder
public record StoreUploadRequest(
        String storeName,
        String address,
        String phoneNumber,
        String email,
        String ownerName
) {

}
