package com.albert.commerce.store.query;

import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreUserId;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
@Entity
@Table(name = "store")
public class StoreAndProduct {

    @EmbeddedId
    private StoreId storeId;

    private String storeName;

    private StoreUserId storeUserId;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "product",
            joinColumns = @JoinColumn(name = "store_id")
    )
    private Set<ProductId> productIds = new HashSet<>();

    public StoreAndProduct() {
    }
}
