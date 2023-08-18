package com.albert.commerce.command.adapter.out.persistence;

import com.albert.commerce.command.adapter.out.persistence.imports.ProductJpaRepository;
import com.albert.commerce.command.application.port.out.ProductRepository;
import com.albert.commerce.command.domain.product.Product;
import com.albert.commerce.command.domain.product.ProductId;
import com.albert.commerce.command.domain.store.StoreId;
import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import java.time.LocalDateTime;
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
        product.updateId(nextId(), LocalDateTime.now(), LocalDateTime.now());
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

    private ProductId nextId() {
        return ProductId.from(sequenceGenerator.generate());
    }
}
