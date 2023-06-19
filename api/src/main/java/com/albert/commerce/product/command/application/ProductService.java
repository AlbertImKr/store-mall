package com.albert.commerce.product.command.application;

import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.command.domain.ProductRepository;
import com.albert.commerce.store.command.domain.StoreId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final SequenceGenerator sequenceGenerator;

    @Transactional
    public CreatedProductResponse addProduct(ProductRequest productRequest, StoreId storeId) {
        Product product = productRepository.save(
                productRequest.toProduct(storeId, new ProductId(sequenceGenerator.generate())));
        return CreatedProductResponse.from(product);
    }

}
