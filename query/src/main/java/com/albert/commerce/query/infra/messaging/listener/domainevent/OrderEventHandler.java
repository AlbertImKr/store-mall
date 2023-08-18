package com.albert.commerce.query.infra.messaging.listener.domainevent;

import com.albert.commerce.query.application.order.OrderFacade;
import com.albert.commerce.query.application.order.dto.OrderCanceledEvent;
import com.albert.commerce.query.application.order.dto.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderEventHandler {

    private final OrderFacade orderFacade;

    @KafkaListener(topics = "OrderCreatedEvent")
    public void handleOrderCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        orderFacade.save(orderCreatedEvent);
    }

    @KafkaListener(topics = "OrderCancelEvent")
    public void handleOrderCancelEvent(OrderCanceledEvent orderCancelEvent) {
        orderFacade.cancel(orderCancelEvent);
    }
}
