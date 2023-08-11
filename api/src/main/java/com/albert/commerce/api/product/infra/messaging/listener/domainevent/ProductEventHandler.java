package com.albert.commerce.api.product.infra.messaging.listener.domainevent;

import com.albert.commerce.api.product.command.domain.ProductCreatedEvent;
import com.albert.commerce.api.product.query.application.ProductFacade;
import com.albert.commerce.api.product.query.domain.ProductData;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductEventHandler {

    private final ProductFacade productFacade;

    @ServiceActivator(inputChannel = "com.albert.commerce.api.store.command.domain.ProductCreatedEvent")
    public void handleProductCreateEvent(ProductCreatedEvent productCreatedEvent) {
        ProductData productData = ProductData.builder()
                .productId(productCreatedEvent.getProductId())
                .productName(productCreatedEvent.getProductName())
                .description(productCreatedEvent.getDescription())
                .category(productCreatedEvent.getCategory())
                .brand(productCreatedEvent.getBrand())
                .price(productCreatedEvent.getPrice())
                .storeId(productCreatedEvent.getStoreId())
                .build();
        productFacade.save(productData);
    }
}
