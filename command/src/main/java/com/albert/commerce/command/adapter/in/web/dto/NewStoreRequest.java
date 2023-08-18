package com.albert.commerce.command.adapter.in.web.dto;

import lombok.Builder;

@Builder
public record NewStoreRequest(String storeName,
                              String ownerName,
                              String address,
                              String phoneNumber,
                              String email) {

}
