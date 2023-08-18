package com.albert.commerce.command.domain.comment;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(of = "value")
@Embeddable
public class CommentId implements Serializable {

    @JsonValue
    private String value;

    private CommentId(String value) {
        this.value = value;
    }

    public static CommentId from(String value) {
        return new CommentId(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
