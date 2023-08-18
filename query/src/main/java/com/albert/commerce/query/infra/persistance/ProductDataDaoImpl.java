package com.albert.commerce.query.infra.persistance;

import com.albert.commerce.query.domain.product.ProductDao;
import com.albert.commerce.query.domain.product.ProductData;
import com.albert.commerce.query.domain.product.ProductId;
import com.albert.commerce.query.domain.store.StoreId;
import com.albert.commerce.query.infra.persistance.imports.ProductDataJpaRepository;
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
