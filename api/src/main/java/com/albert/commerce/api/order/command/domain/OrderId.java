package com.albert.commerce.api.order.command.domain;

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
public class OrderId implements Serializable {
    private String id;

    private OrderId(String orderId) {
        this.id = orderId;
    }

    public static OrderId from(String orderId) {
        return new OrderId(orderId);
    }

    @JsonValue
    public String getId() {
        return id;
    }
}
