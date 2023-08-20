package com.albert.commerce.domain.store;

import com.albert.commerce.domain.event.DomainEvent;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Builder;

public class StoreUploadedEvent extends DomainEvent {

    private final StoreId storeId;
    private final String storeName;
    private final String address;
    private final String phoneNumber;
    private final String email;
    private final String ownerName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private final LocalDateTime updatedTime;

    @Builder
    private StoreUploadedEvent(StoreId storeId, String storeName, String address, String phoneNumber, String email,
            String ownerName, LocalDateTime updatedTime) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.ownerName = ownerName;
        this.updatedTime = updatedTime;
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

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }
}
