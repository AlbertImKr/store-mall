package com.albert.commerce.domain.comment;

import com.albert.commerce.domain.AggregateId;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class CommentId extends AggregateId {

    private CommentId(String value) {
        super(value);
    }

    public static CommentId from(String value) {
        return new CommentId(value);
    }
}
