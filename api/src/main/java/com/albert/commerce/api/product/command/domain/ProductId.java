package com.albert.commerce.api.product.command.domain;

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
public class ProductId implements Serializable {

    private String id;


    private ProductId(String id) {
        this.id = id;
    }

    public static ProductId from(String productId) {
        return new ProductId(productId);
    }

    @JsonValue
    public String getId() {
        return id;
    }
}
