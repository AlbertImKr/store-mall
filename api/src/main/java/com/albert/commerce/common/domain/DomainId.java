package com.albert.commerce.common.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "value")
@Embeddable
public class DomainId implements Serializable {

    @JsonValue
    private String value;

    private DomainId(String value) {
        this.value = value;
    }

    public static DomainId from(String value) {
        return new DomainId(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
