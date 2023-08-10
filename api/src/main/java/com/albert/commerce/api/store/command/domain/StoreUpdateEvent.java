package com.albert.commerce.api.store.command.domain;

import com.albert.commerce.api.common.domain.DomainId;
import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import lombok.Builder;

public class StoreUpdateEvent extends DomainEvent {

    DomainId storeId;
    String storeName;
    String address;
    String phoneNumber;
    String email;
    String ownerName;

    @Builder
    private StoreUpdateEvent(DomainId storeId, String storeName, String address, String phoneNumber, String email,
            String ownerName) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ownerName = ownerName;
    }

    public DomainId getStoreId() {
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
