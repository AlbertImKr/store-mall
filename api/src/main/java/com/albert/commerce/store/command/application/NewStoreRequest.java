package com.albert.commerce.store.command.application;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreUserId;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class NewStoreRequest {

    @NotNull
    private String storeName;
    @NotNull
    private String ownerName;
    @NotNull
    private String address;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String email;

    @Builder
    private NewStoreRequest(String storeName, String ownerName, String address, String phoneNumber,
            String email) {
        this.storeName = storeName;
        this.ownerName = ownerName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Store toStore(StoreUserId storeUserId, StoreId storeId) {
        return Store
                .builder()
                .storeName(storeName)
                .ownerName(ownerName)
                .address(address)
                .email(email)
                .phoneNumber(phoneNumber)
                .storeUserId(storeUserId)
                .storeId(storeId)
                .build();
    }
}
