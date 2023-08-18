package com.albert.commerce.query.infra.persistance.imports;

import com.albert.commerce.query.domain.product.ProductData;
import com.albert.commerce.query.domain.product.ProductId;
import com.albert.commerce.query.domain.store.StoreId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDataJpaRepository extends JpaRepository<ProductData, ProductId> {

    Page<ProductData> findByStoreId(StoreId storeId, Pageable pageable);
}
