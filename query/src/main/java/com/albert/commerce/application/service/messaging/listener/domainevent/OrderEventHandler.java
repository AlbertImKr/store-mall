package com.albert.commerce.application.service.messaging.listener.domainevent;

import com.albert.commerce.application.service.order.OrderFacade;
import com.albert.commerce.application.service.order.dto.OrderCanceledEvent;
import com.albert.commerce.application.service.order.dto.OrderPlacedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderEventHandler {

    private final OrderFacade orderFacade;

    @KafkaListener(topics = "OrderPlacedEvent")
    public void handleOrderPlacedEvent(OrderPlacedEvent orderPlacedEvent) {
        orderFacade.place(orderPlacedEvent);
    }

    @KafkaListener(topics = "OrderCanceledEvent")
    public void handleOrderCanceledEvent(OrderCanceledEvent orderCancelEvent) {
        orderFacade.cancel(orderCancelEvent);
    }
}
