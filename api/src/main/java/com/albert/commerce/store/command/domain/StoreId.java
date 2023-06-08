package com.albert.commerce.store.command.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class StoreId implements Serializable {

    @Column(name = "store_id")
    private UUID storeId;

    public StoreId() {
        this.storeId = UUID.randomUUID();
    }

    public UUID getStoreId() {
        return storeId;
    }
}
