package com.albert.commerce.adapter.out.persistance;

import com.albert.commerce.adapter.out.persistance.imports.ProductDataJpaRepository;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductDao;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
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
    public Optional<Product> findById(ProductId productId) {
        return productDataJpaRepository.findById(productId);
    }

    @Override
    public boolean exists(ProductId productId) {
        return productDataJpaRepository.existsById(productId);
    }

    @Override
    public Product save(Product product) {
        return productDataJpaRepository.save(product);
    }

    @Override
    public Page<Product> findByStoreId(StoreId storeId, Pageable pageable) {
        return productDataJpaRepository.findByStoreId(storeId, pageable);
    }
}
