package com.albert.commerce.adapter.out.persistance;

import com.albert.commerce.adapter.out.persistance.imports.ProductJpaRepository;
import com.albert.commerce.application.port.out.ProductDao;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductDaoImpl implements ProductDao {

    private final ProductJpaRepository productJpaRepository;

    @Override
    public Optional<Product> findById(ProductId productId) {
        return productJpaRepository.findById(productId);
    }
}
