package com.albert.commerce.application.service.product;

import com.albert.commerce.application.service.product.dto.ProductCreatedEvent;
import com.albert.commerce.application.service.product.dto.ProductUpdatedEvent;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.product.ProductDao;
import com.albert.commerce.domain.product.ProductId;
import com.albert.commerce.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class ProductFacade {

    private final ProductDao productDao;

    @Transactional
    public void create(ProductCreatedEvent productCreatedEvent) {
        var product = toProduct(productCreatedEvent);
        productDao.save(product);
    }

    @Transactional
    public void update(ProductUpdatedEvent productUpdatedEvent) {
        var product = getProductByProductId(productUpdatedEvent.productId());
        update(productUpdatedEvent, product);
    }

    @Transactional(readOnly = true)
    public Product getProductByProductId(ProductId productId) {
        return productDao.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }

    private static void update(ProductUpdatedEvent productUpdatedEvent, Product product) {
        product.update(
                productUpdatedEvent.productName(),
                productUpdatedEvent.description(),
                productUpdatedEvent.brand(),
                productUpdatedEvent.price(),
                productUpdatedEvent.category(),
                productUpdatedEvent.updateTime()
        );
    }

    private static Product toProduct(ProductCreatedEvent productCreatedEvent) {
        return Product.builder()
                .productId(productCreatedEvent.productId())
                .productName(productCreatedEvent.productName())
                .description(productCreatedEvent.description())
                .category(productCreatedEvent.category())
                .brand(productCreatedEvent.brand())
                .price(productCreatedEvent.price())
                .storeId(productCreatedEvent.storeId())
                .build();
    }
}
