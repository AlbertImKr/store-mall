package com.albert.commerce.api.store.command.application.dto;

import lombok.Builder;

@Builder
public record NewStoreRequest(String storeName,
                              String ownerName,
                              String address,
                              String phoneNumber,
                              String email) {

}
