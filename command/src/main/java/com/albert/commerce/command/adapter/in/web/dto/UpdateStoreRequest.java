package com.albert.commerce.command.adapter.in.web.dto;

import lombok.Builder;

@Builder
public record UpdateStoreRequest(

        String storeName,
        String address,
        String phoneNumber,
        String email,
        String ownerName) {

}
