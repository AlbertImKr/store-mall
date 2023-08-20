package com.albert.commerce.domain.store;

import com.albert.commerce.domain.event.DomainEvent;
import lombok.Builder;

public class StoreUploadedEvent extends DomainEvent {

    StoreId storeId;
    String storeName;
    String address;
    String phoneNumber;
    String email;
    String ownerName;

    @Builder
    private StoreUploadedEvent(StoreId storeId, String storeName, String address, String phoneNumber, String email,
            String ownerName) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ownerName = ownerName;
    }

    public StoreId getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getOwnerName() {
        return ownerName;
    }
}
