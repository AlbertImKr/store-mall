package com.albert.commerce.domain.store;

import com.albert.commerce.domain.AggregateId;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class StoreId extends AggregateId {

    private StoreId(String value) {
        super(value);
    }

    public static StoreId from(String value) {
        return new StoreId(value);
    }
}
