package com.albert.commerce.store.command.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class StoreId implements Serializable {

    @Column(name = "store_id")
    private UUID value;

    public StoreId() {
        this.value = UUID.randomUUID();
    }

    private StoreId(UUID value) {
        this.value = value;
    }

    public static StoreId from(UUID storeId) {
        return new StoreId(storeId);
    }

    @JsonValue
    public UUID getValue() {
        return value;
    }
}
