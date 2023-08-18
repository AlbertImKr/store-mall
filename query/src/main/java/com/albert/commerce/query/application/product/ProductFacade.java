package com.albert.commerce.query.application.product;

import com.albert.commerce.query.application.product.dto.ProductCreatedEvent;
import com.albert.commerce.query.application.product.dto.ProductUpdatedEvent;
import com.albert.commerce.query.common.exception.ProductNotFoundException;
import com.albert.commerce.query.domain.product.ProductDao;
import com.albert.commerce.query.domain.product.ProductData;
import com.albert.commerce.query.domain.product.ProductId;
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
        ProductData productData = ProductData.builder()
                .productId(productCreatedEvent.productId())
                .productName(productCreatedEvent.productName())
                .description(productCreatedEvent.description())
                .category(productCreatedEvent.category())
                .brand(productCreatedEvent.brand())
                .price(productCreatedEvent.price())
                .storeId(productCreatedEvent.storeId())
                .build();
        productDao.save(productData);
    }

    @Transactional
    public void update(ProductUpdatedEvent productUpdatedEvent) {
        ProductData productData = productDao.findById(productUpdatedEvent.productId())
                .orElseThrow(ProductNotFoundException::new);
        productData.update(
                productUpdatedEvent.productName(),
                productUpdatedEvent.description(),
                productUpdatedEvent.brand(),
                productUpdatedEvent.price(),
                productUpdatedEvent.category(),
                productUpdatedEvent.updateTime()
        );
    }

    public ProductData getByProductId(ProductId productId) {
        return productDao.findById(productId)
                .orElseThrow(ProductNotFoundException::new);
    }
}
