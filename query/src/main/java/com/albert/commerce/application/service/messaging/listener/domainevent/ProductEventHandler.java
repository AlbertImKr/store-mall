package com.albert.commerce.application.service.messaging.listener.domainevent;

import com.albert.commerce.application.service.product.ProductFacade;
import com.albert.commerce.application.service.product.dto.ProductCreatedEvent;
import com.albert.commerce.application.service.product.dto.ProductUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductEventHandler {

    private final ProductFacade productFacade;

    @KafkaListener(topics = "ProductCreatedEvent")
    public void handleProductCreatedEvent(ProductCreatedEvent productCreatedEvent) {
        productFacade.save(productCreatedEvent);
    }

    @KafkaListener(topics = "ProductUpdatedEvent")
    public void handleProductUpdatedEvent(ProductUpdatedEvent productUpdatedEvent) {
        productFacade.update(productUpdatedEvent);
    }
}
