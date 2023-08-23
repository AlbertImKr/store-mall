package com.albert.commerce.domain.store;

import com.albert.commerce.application.service.store.StoreRegisterCommand;
import com.albert.commerce.domain.event.Events;
import com.albert.commerce.domain.user.UserId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "store")
public class Store {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "store_id"))
    private StoreId storeId;
    @Column(nullable = false)
    private String storeName;
    @Column(nullable = false)
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    private UserId userId;
    @Column(nullable = false)
    private String ownerName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String email;
    protected LocalDateTime createdTime;
    protected LocalDateTime updatedTime;

    @Builder
    private Store(StoreId storeId, String storeName, UserId userId, String ownerName, String address,
            String phoneNumber,
            String email, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.userId = userId;
        this.ownerName = ownerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        Events.raise(toStoreRegisteredEvent());
    }

    public void upload(String storeName, String ownerName, String address, String email, String phoneNumber,
            LocalDateTime updatedTime) {
        this.storeName = storeName;
        this.ownerName = ownerName;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.updatedTime = updatedTime;
        Events.raise(toStoreUploadedEvent());
    }

    public StoreId getStoreId() {
        return storeId;
    }

    private StoreRegisteredEvent toStoreRegisteredEvent() {
        return new StoreRegisteredEvent(
                storeId,
                storeName,
                userId,
                ownerName,
                address,
                phoneNumber,
                email,
                createdTime,
                updatedTime
        );
    }

    private StoreUploadedEvent toStoreUploadedEvent() {
        return new StoreUploadedEvent(
                storeId,
                storeName,
                address,
                phoneNumber,
                email,
                ownerName,
                updatedTime
        );
    }

    public static Store from(StoreId storeId, StoreRegisterCommand storeRegisterCommand, UserId userId) {
        return Store.builder()
                .storeId(storeId)
                .userId(userId)
                .storeName(storeRegisterCommand.getStoreName())
                .ownerName(storeRegisterCommand.getOwnerName())
                .address(storeRegisterCommand.getAddress())
                .phoneNumber(storeRegisterCommand.getPhoneNumber())
                .email(storeRegisterCommand.getEmail())
                .createdTime(LocalDateTime.now())
                .updatedTime(LocalDateTime.now())
                .build();
    }

}
