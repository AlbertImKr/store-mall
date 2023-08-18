package com.albert.commerce.command.domain.store;

import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import lombok.Builder;

public class StoreUpdateEvent extends DomainEvent {

    StoreId storeId;
    String storeName;
    String address;
    String phoneNumber;
    String email;
    String ownerName;

    @Builder
    private StoreUpdateEvent(StoreId storeId, String storeName, String address, String phoneNumber, String email,
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
