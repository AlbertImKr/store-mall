package com.albert.commerce.command.infra.persistence.imports;

import com.albert.commerce.command.domain.product.Product;
import com.albert.commerce.command.domain.product.ProductId;
import com.albert.commerce.command.domain.store.StoreId;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<Product, ProductId> {

    Optional<Product> findByStoreIdAndProductId(StoreId storeId, ProductId productId);
}
