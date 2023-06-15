package com.albert.commerce.store.command.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StoreId implements Serializable {

    @Column(name = "store_id")
    private String value;

    public StoreId(String value) {
        this.value = value;
    }

    public static StoreId from(String storeId) {
        return new StoreId(storeId);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
