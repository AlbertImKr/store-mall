package com.albert.commerce.api.common.infra.persistence;

import com.fasterxml.jackson.annotation.JsonValue;

public record Money(@JsonValue long value) {

    public static Money from(long value) {
        return new Money(value);
    }

    public Money multiply(Long quantity) {
        return from(value * quantity);
    }
}
