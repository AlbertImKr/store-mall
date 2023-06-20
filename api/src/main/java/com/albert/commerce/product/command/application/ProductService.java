package com.albert.commerce.product.command.application;

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
    public ProdutCreatedResponse addProduct(ProductRequest productRequest, StoreId storeId) {
        Product product = productRepository.save(
                productRequest.toProduct(storeId, productRepository.nextId()));
        return ProdutCreatedResponse.from(product);
    }

    @Transactional
    public ProductResponse update(Product product, ProductRequest productRequest) {
        Product changedProduct = product.update(
                productRequest.productName(),
                productRequest.price(),
                productRequest.brand(),
                productRequest.category(),
                productRequest.description());
        Product savedProduct = productRepository.save(changedProduct);
        return ProductResponse.from(savedProduct);
    }
}
