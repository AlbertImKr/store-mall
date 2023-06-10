package com.albert.commerce.store.command.domain;

import com.albert.commerce.product.command.domain.ProductId;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
@Entity
public class Store {


    @EmbeddedId
    private StoreId storeId;

    private String storeName;

    private StoreUserId storeUserId;

    @ElementCollection
    @CollectionTable(
            name = "product",
            joinColumns = @JoinColumn(name = "store_id")
    )
    private Set<ProductId> productIds = new HashSet<>();

    protected Store() {
    }

    public Store(String storeName, StoreUserId storeUserId) {
        this.storeId = new StoreId();
        this.storeName = storeName;
        this.storeUserId = storeUserId;
    }

    public void addProductId(ProductId productId) {
        productIds.add(productId);
    }

    public void removeProductId(ProductId productId) {
        productIds.remove(productId);
    }
}
