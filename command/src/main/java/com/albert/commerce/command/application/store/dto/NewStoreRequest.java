package com.albert.commerce.command.application.store.dto;

import lombok.Builder;

@Builder
public record NewStoreRequest(String storeName,
                              String ownerName,
                              String address,
                              String phoneNumber,
                              String email) {

}
