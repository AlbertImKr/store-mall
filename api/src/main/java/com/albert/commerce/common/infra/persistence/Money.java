package com.albert.commerce.common.infra.persistence;

import com.fasterxml.jackson.annotation.JsonValue;

public record Money(long value) {

    @Override
    @JsonValue
    public long value() {
        return value;
    }

}
