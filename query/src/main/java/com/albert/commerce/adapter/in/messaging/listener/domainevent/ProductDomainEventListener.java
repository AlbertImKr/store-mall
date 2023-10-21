package com.albert.commerce.adapter.in.messaging.listener.domainevent;

import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.ProductCreatedEvent;
import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.ProductUpdatedEvent;
import com.albert.commerce.adapter.out.config.cache.CacheValue;
import com.albert.commerce.adapter.out.persistence.imports.ProductJpaRepository;
import com.albert.commerce.application.service.exception.error.ProductNotFoundException;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.units.DomainEventChannelNames;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductDomainEventListener {

    private final ProductJpaRepository productJpaRepository;

    @ServiceActivator(inputChannel = DomainEventChannelNames.PRODUCT_CREATED_EVENT)
    public void handleProductCreatedEvent(ProductCreatedEvent productCreatedEvent) {
        var product = toProduct(productCreatedEvent);
        productJpaRepository.save(product);
    }

    @Transactional
    @CacheEvict(value = CacheValue.PRODUCT, key = "#productUpdatedEvent.productId().value")
    @ServiceActivator(inputChannel = DomainEventChannelNames.PRODUCT_UPDATED_EVENT)
    public void handleProductUpdatedEvent(ProductUpdatedEvent productUpdatedEvent) {
        var product = productJpaRepository.findById(productUpdatedEvent.productId())
                .orElseThrow(ProductNotFoundException::new);
        update(productUpdatedEvent, product);
    }

    private static void update(ProductUpdatedEvent productUpdatedEvent, Product product) {
        product.update(
                productUpdatedEvent.productName(),
                productUpdatedEvent.description(),
                productUpdatedEvent.brand(),
                productUpdatedEvent.price(),
                productUpdatedEvent.category(),
                productUpdatedEvent.updatedTime()
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
                .createdTime(productCreatedEvent.createdTime())
                .updatedTime(productCreatedEvent.updatedTime())
                .build();
    }
}
