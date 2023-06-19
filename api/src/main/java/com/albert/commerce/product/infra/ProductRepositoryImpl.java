package com.albert.commerce.product.infra;

import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.command.domain.ProductRepository;
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
    public ProductId nextId() {
        return new ProductId(sequenceGenerator.generate());
    }
}
