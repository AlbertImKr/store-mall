package com.albert.commerce.api.store.command.domain;

import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import lombok.Builder;

public class StoreCreatedEvent extends DomainEvent {

    private final DomainId storeId;
    private final String storeName;
    private final DomainId userId;
    private final String ownerName;
    private final String address;
    private final String phoneNumber;
    private final String email;

    @Builder
    private StoreCreatedEvent(DomainId storeId, String storeName, DomainId userId, String ownerName, String address,
            String phoneNumber, String email) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.userId = userId;
        this.ownerName = ownerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public DomainId getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public DomainId getUserId() {
        return userId;
    }

    public String getOwnerName() {
        return ownerName;
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
}
