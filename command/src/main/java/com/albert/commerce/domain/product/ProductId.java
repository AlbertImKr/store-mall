package com.albert.commerce.domain.product;

import com.albert.commerce.domain.AggregateId;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ProductId extends AggregateId {

    private ProductId(String value) {
        super(value);
    }

    public static ProductId from(String value) {
        return new ProductId(value);
    }
}
