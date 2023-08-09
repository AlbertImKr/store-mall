package com.albert.commerce.api.store.command.domain;

import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import java.time.LocalDateTime;
import lombok.Builder;

public class StoreCreatedEvent extends DomainEvent {

    protected LocalDateTime updateTime;
    private StoreId storeId;
    private String storeName;
    private UserId userId;
    private String ownerName;
    private String address;
    private String phoneNumber;
    private String email;
    private LocalDateTime createdTime;

    @Builder
    private StoreCreatedEvent(StoreId storeId, String storeName, UserId userId, String ownerName, String address,
            String phoneNumber, String email, LocalDateTime createdTime, LocalDateTime updateTime) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.userId = userId;
        this.ownerName = ownerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.createdTime = createdTime;
        this.updateTime = updateTime;
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

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }
}
