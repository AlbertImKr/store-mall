package com.albert.commerce.api.store.command.domain;

import com.albert.commerce.api.common.domain.DomainId;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.shared.messaging.domain.event.Events;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "store")
public class Store {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "store_id"))
    private DomainId storeId;
    @Column(nullable = false)
    private String storeName;
    @Column(nullable = false)
    private UserId userId;
    @Column(nullable = false)
    private String ownerName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String email;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime createdTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime updateTime;

    @Builder
    private Store(DomainId storeId, String storeName, UserId userId, String ownerName,
            String address, String phoneNumber, String email) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.userId = userId;
        this.ownerName = ownerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public void updateId(DomainId storeId) {
        this.storeId = storeId;
        this.createdTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
        StoreCreatedEvent storeCreatedEvent = StoreCreatedEvent.builder()
                .storeId(storeId)
                .userId(userId)
                .storeName(storeName)
                .ownerName(ownerName)
                .address(address)
                .phoneNumber(phoneNumber)
                .email(email)
                .build();
        Events.raise(storeCreatedEvent);
    }

    public void update(String storeName, String ownerName, String address, String email, String phoneNumber) {
        this.storeName = storeName;
        this.ownerName = ownerName;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        StoreUpdateEvent storeUpdateEvent = StoreUpdateEvent.builder()
                .storeId(storeId)
                .storeName(storeName)
                .ownerName(ownerName)
                .address(address)
                .email(email)
                .phoneNumber(phoneNumber)
                .build();
        Events.raise(storeUpdateEvent);
    }
}
