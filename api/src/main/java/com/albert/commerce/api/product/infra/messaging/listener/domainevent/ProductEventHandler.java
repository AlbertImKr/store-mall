package com.albert.commerce.api.product.infra.messaging.listener.domainevent;

import com.albert.commerce.api.product.command.domain.ProductCreatedEvent;
import com.albert.commerce.api.product.command.domain.ProductUpdatedEvent;
import com.albert.commerce.api.product.query.application.ProductFacade;
import com.albert.commerce.api.product.query.application.dto.ProductUpdateRequest;
import com.albert.commerce.api.product.query.domain.ProductData;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductEventHandler {

    private final ProductFacade productFacade;

    @ServiceActivator(inputChannel = "ProductCreatedEvent")
    public void handleProductCreatedEvent(ProductCreatedEvent productCreatedEvent) {
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

    @ServiceActivator(inputChannel = "ProductUpdatedEvent")
    public void handleProductUpdatedEvent(ProductUpdatedEvent productUpdatedEvent) {
        ProductUpdateRequest productUpdateRequest = ProductUpdateRequest.builder()
                .productId(productUpdatedEvent.getProductId())
                .productName(productUpdatedEvent.getProductName())
                .description(productUpdatedEvent.getDescription())
                .category(productUpdatedEvent.getCategory())
                .brand(productUpdatedEvent.getBrand())
                .price(productUpdatedEvent.getPrice())
                .updateTime(productUpdatedEvent.getUpdateTime())
                .build();
        productFacade.update(productUpdateRequest);
    }
}
