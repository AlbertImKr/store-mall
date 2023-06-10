package com.albert.commerce.user.command.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;

@Embeddable
public class UserId implements Serializable {

    @Column(name = "user_id")
    private UUID id;

    public UserId() {
        this.id = UUID.randomUUID();
    }

    @JsonValue
    public UUID getId() {
        return id;
    }
}