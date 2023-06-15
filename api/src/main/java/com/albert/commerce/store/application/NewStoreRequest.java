package com.albert.commerce.store.application;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreUserId;
import com.albert.commerce.user.command.domain.UserId;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class NewStoreRequest {

    private UserId userId;
    @NotNull
    private String storeName;

    public NewStoreRequest(String storeName) {
        this.storeName = storeName;
    }

    public Store toStore(StoreId storeId) {
        return new Store(storeId, storeName, new StoreUserId(userId));
    }

    public StoreUserId getStoreUserId() {
        return new StoreUserId(userId);
    }
}
