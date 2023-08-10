package com.albert.commerce.api.product.command.domain;

import com.albert.commerce.api.product.command.application.dto.ProductCreatedResponse;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Access(AccessType.FIELD)
public class ProductId extends ProductCreatedResponse implements Serializable {

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
