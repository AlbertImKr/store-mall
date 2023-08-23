package com.albert.commerce.adapter.out.persistence;

import com.albert.commerce.adapter.out.persistence.imports.ProductJpaRepository;
import com.albert.commerce.application.port.out.ProductRepository;
import com.albert.commerce.application.port.out.persistence.SequenceGenerator;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.domain.store.StoreId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepository {

    private final SequenceGenerator sequenceGenerator;
    private final ProductJpaRepository productJpaRepository;


    @Override
    public Product save(Product product) {
        return productJpaRepository.save(product);
    }

    @Override
    public Optional<Product> findByProductId(ProductId productId) {
        return productJpaRepository.findById(productId);
    }

    @Override
    public boolean existsById(ProductId productId) {
        return productJpaRepository.existsById(productId);
    }

    @Override
    public Optional<Product> findByStoreIdAndProductId(StoreId storeId, ProductId productId) {
        return productJpaRepository.findByStoreIdAndProductId(storeId, productId);
    }

    @Override
    public ProductId nextId() {
        return ProductId.from(sequenceGenerator.generate());
    }
}
