package com.albert.commerce.domain.command.store;

import com.albert.commerce.domain.command.user.UserId;
import com.albert.commerce.messagingcore.domain.event.DomainEvent;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreCreatedEvent implements DomainEvent {

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

    public static StoreCreatedEvent from(Store store) {
        return StoreCreatedEvent.builder()
                .storeId(store.getStoreId())
                .storeName(store.getStoreName())
                .userId(store.getUserId())
                .ownerName(store.getOwnerName())
                .address(store.getAddress())
                .phoneNumber(store.getPhoneNumber())
                .email(store.getEmail())
                .createdTime(store.createdTime)
                .updateTime(store.updateTime)
                .build();
    }
}
