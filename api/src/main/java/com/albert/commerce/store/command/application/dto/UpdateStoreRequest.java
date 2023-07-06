package com.albert.commerce.store.command.application.dto;

import lombok.Builder;

@Builder
public record UpdateStoreRequest(

        String storeName,
        String address,
        String phoneNumber,
        String email,
        String ownerName) {

}
