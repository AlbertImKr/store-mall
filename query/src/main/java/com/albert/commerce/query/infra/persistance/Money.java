package com.albert.commerce.query.infra.persistance;

import com.fasterxml.jackson.annotation.JsonValue;

public record Money(@JsonValue long value) {

    public static Money from(long value) {
        return new Money(value);
    }

    public Money multiply(Long quantity) {
        return from(value * quantity);
    }
}
