package com.albert.commerce.application.command.store.dto;

import lombok.Builder;

@Builder
public record NewStoreRequest(String storeName,
                              String ownerName,
                              String address,
                              String phoneNumber,
                              String email) {

}
