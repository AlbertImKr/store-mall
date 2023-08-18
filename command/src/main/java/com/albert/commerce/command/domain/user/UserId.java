package com.albert.commerce.command.domain.user;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "value")
@Embeddable
public class UserId implements Serializable {

    @JsonValue
    private String value;

    private UserId(String value) {
        this.value = value;
    }

    public static UserId from(String value) {
        return new UserId(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
