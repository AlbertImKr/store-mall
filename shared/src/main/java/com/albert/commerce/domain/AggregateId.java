package com.albert.commerce.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.MappedSuperclass;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(of = "value")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class AggregateId implements Serializable {

    @JsonValue
    private String value;

    protected AggregateId(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
