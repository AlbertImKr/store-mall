package com.albert.commerce.product.domain;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.UUID;
import lombok.Getter;

@Getter
@Embeddable
@Access(AccessType.FIELD)
public class ProductId implements Serializable {

    @Column(name = "product_id")
    private UUID id;

    public ProductId() {
        this.id = UUID.randomUUID();
    }


}
