package com.albert.commerce.domain.command.store;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "id")
@Embeddable
public class StoreId implements Serializable {

    @Column(name = "store_id", nullable = false)
    private String id;

    public StoreId(String id) {
        this.id = id;
    }

    public static StoreId from(String storeId) {
        return new StoreId(storeId);
    }

    @JsonValue
    public String getId() {
        return id;
    }
}
