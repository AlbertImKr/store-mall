package com.albert.commerce.domain.user;

import com.albert.commerce.domain.AggregateId;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class UserId extends AggregateId {

    private UserId(String value) {
        super(value);
    }

    public static UserId from(String value) {
        return new UserId(value);
    }
}
