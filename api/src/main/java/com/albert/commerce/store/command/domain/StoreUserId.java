package com.albert.commerce.store.command.domain;

import com.albert.commerce.user.command.domain.UserId;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
public class StoreUserId {

    @Embedded
    private UserId userId;

    protected StoreUserId() {
    }

    public StoreUserId(UserId userId) {
        this.userId = userId;
    }

    @JsonValue
    public UserId getUserId() {
        return userId;
    }
}
