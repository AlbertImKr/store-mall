package com.albert.commerce.adapter.out.persistence;

import com.fasterxml.jackson.annotation.JsonValue;
import java.io.Serializable;

public record Money(@JsonValue long value) implements Serializable {

    public static Money from(long value) {
        return new Money(value);
    }

    public Money multiply(Long quantity) {
        return from(value * quantity);
    }
}
