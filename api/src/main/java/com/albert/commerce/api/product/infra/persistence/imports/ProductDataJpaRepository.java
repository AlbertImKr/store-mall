package com.albert.commerce.api.product.infra.persistence.imports;

import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.product.query.domain.ProductData;
import com.albert.commerce.api.store.command.domain.StoreId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDataJpaRepository extends JpaRepository<ProductData, ProductId> {

    Page<ProductData> findByStoreId(StoreId storeId, Pageable pageable);
}
