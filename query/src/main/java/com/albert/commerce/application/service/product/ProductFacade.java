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

    @Transactional(readOnly = true)
    public void checkId(ProductId productId) {
        if (!productDao.exists(productId)) {
            throw new ProductNotFoundException();
        }
    }

    @Transactional
    public void save(ProductCreatedEvent productCreatedEvent) {
        Product product = Product.builder()
                .productId(productCreatedEvent.productId())
                .productName(productCreatedEvent.productName())
                .description(productCreatedEvent.description())
                .category(productCreatedEvent.category())
                .brand(productCreatedEvent.brand())
                .price(productCreatedEvent.price())
                .storeId(productCreatedEvent.storeId())
                .build();
        productDao.save(product);
    }

    @Transactional
    public void update(ProductUpdatedEvent productUpdatedEvent) {
        Product product = productDao.findById(productUpdatedEvent.productId())
                .orElseThrow(ProductNotFoundException::new);
        product.update(
                productUpdatedEvent.productName(),
                productUpdatedEvent.description(),
                productUpdatedEvent.brand(),
                productUpdatedEvent.price(),
                productUpdatedEvent.category(),
                productUpdatedEvent.updateTime()
        );
    }

    public Product getByProductId(ProductId productId) {
        return productDao.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }
}
