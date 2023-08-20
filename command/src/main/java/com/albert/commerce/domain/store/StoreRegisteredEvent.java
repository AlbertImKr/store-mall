package com.albert.commerce.domain.store;

import com.albert.commerce.domain.event.DomainEvent;
import com.albert.commerce.domain.user.UserId;
import lombok.Builder;

public class StoreRegisteredEvent extends DomainEvent {

    private final StoreId storeId;
    private final String storeName;
    private final UserId userId;
    private final String ownerName;
    private final String address;
    private final String phoneNumber;
    private final String email;

    @Builder
    private StoreRegisteredEvent(StoreId storeId, String storeName, UserId userId, String ownerName, String address,
            String phoneNumber, String email) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.userId = userId;
        this.ownerName = ownerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public StoreId getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public UserId getUserId() {
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
