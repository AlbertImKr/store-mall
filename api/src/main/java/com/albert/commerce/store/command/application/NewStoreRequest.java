package com.albert.commerce.store.command.application;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreUserId;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class NewStoreRequest {

    @NotNull
    private String storeName;

    public NewStoreRequest(String storeName) {
        this.storeName = storeName;
    }

    public Store toStore(StoreUserId storeUserId, StoreId storeId) {
        return new Store(storeId, storeName, storeUserId);
    }
}
