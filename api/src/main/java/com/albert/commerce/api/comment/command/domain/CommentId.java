package com.albert.commerce.api.comment.command.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Access(AccessType.FIELD)
@EqualsAndHashCode(of = "id")
public class CommentId implements Serializable {

    private String id;

    public CommentId(String id) {
        this.id = id;
    }

    public static CommentId from(String value) {
        return new CommentId(value);
    }

    @JsonValue
    public String getId() {
        return id;
    }
}
