package com.albert.commerce.domain.order;

import com.albert.commerce.domain.AggregateId;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class OrderId extends AggregateId {

    private OrderId(String value) {
        super(value);
    }

    public static OrderId from(String value) {
        return new OrderId(value);
    }
}
