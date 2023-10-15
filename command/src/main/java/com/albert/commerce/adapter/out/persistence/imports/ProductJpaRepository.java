package com.albert.commerce.adapter.out.persistence.imports;

import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, ProductId> {

    Optional<Product> findByProductIdAndStoreId(ProductId productId, StoreId storeId);
}
