package com.albert.commerce.command.application.store.dto;

import lombok.Builder;

@Builder
public record UpdateStoreRequest(

        String storeName,
        String address,
        String phoneNumber,
        String email,
        String ownerName) {

}
