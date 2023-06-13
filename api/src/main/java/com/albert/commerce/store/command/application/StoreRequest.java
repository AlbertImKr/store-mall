package com.albert.commerce.store.command.application;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreUserId;
import com.albert.commerce.user.command.domain.UserId;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoreRequest {

    private UserId userId;
    @NotNull
    private String storeName;

    public StoreRequest() {
    }

    public StoreRequest(String storeName) {
        this.storeName = storeName;
    }

    public Store toStore() {
        return new Store(storeName, new StoreUserId(userId));
    }

    public StoreUserId getStoreUserId() {
        return new StoreUserId(userId);
    }
}
