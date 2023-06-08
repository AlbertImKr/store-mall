package com.albert.commerce.store.command.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class Store {


    @EmbeddedId
    @JsonValue
    private StoreId storeId;

    private String storeName;

    private StoreUserId storeUserId;

    protected Store() {
    }

    public Store(String storeName, StoreUserId storeUserId) {
        this.storeId = new StoreId();
        this.storeName = storeName;
        this.storeUserId = storeUserId;
    }
}
