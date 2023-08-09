package com.albert.commerce.api.user.command.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode(of = "id")
public class UserId implements Serializable {

    @Column(name = "user_id", nullable = false)
    private String id;

    private UserId(String id) {
        this.id = id;
    }

    public static UserId from(String generate) {
        return new UserId(generate);
    }

    @JsonValue
    public String getId() {
        return id;
    }
}
