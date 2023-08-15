package com.albert.commerce.api.product.infra.persistence;

import com.albert.commerce.api.product.command.domain.ProductId;
import com.albert.commerce.api.product.infra.persistence.imports.ProductDataJpaRepository;
import com.albert.commerce.api.product.query.domain.ProductDao;
import com.albert.commerce.api.product.query.domain.ProductData;
import com.albert.commerce.api.store.command.domain.StoreId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductDataDaoImpl implements ProductDao {

    private final ProductDataJpaRepository productDataJpaRepository;

    @Override
    public Optional<ProductData> findById(ProductId productId) {
        return productDataJpaRepository.findById(productId);
    }

    @Override
    public boolean exists(ProductId productId) {
        return productDataJpaRepository.existsById(productId);
    }

    @Override
    public ProductData save(ProductData productData) {
        return productDataJpaRepository.save(productData);
    }

    @Override
    public Page<ProductData> findByStoreId(StoreId storeId, Pageable pageable) {
        return productDataJpaRepository.findByStoreId(storeId, pageable);
    }
}
