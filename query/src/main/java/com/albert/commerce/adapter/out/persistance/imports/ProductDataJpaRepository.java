package com.albert.commerce.adapter.out.persistance.imports;

import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDataJpaRepository extends JpaRepository<Product, ProductId> {

    Page<Product> findByStoreId(StoreId storeId, Pageable pageable);
}
