package com.albert.commerce.store.command.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "value")
@Embeddable
public class StoreId implements Serializable {

    @Column(name = "store_id", nullable = false)
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
