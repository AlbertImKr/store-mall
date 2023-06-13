package com.albert.commerce.product.application;

import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductRepository;
import com.albert.commerce.store.command.domain.StoreId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public ProductResponse addProduct(ProductRequest productRequest, StoreId storeId) {
        Product product = productRepository.save(productRequest.toProduct(storeId));
        return ProductResponse.from(product);
    }

}
