package com.albert.commerce.store.command.application;

import lombok.Builder;

public record UpdateStoreRequest(

        String storeName,
        String address,
        String phoneNumber,
        String email,
        String ownerName) {


    @Builder
    public UpdateStoreRequest(String storeName, String address, String phoneNumber, String email,
            String ownerName) {
        this.storeName = storeName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ownerName = ownerName;
    }
}
