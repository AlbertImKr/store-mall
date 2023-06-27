package com.albert.commerce.comment.command.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Access(AccessType.FIELD)
public class CommentId implements Serializable {

    @Column(name = "comment_id", nullable = false)
    private String value;

    public CommentId(String value) {
        this.value = value;
    }

    private static CommentId from(String value) {
        return new CommentId(value);
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}
