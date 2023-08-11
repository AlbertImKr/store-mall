package com.albert.commerce.api.order.infra.messaging.listener.domainevent;

import com.albert.commerce.api.order.command.domain.OrderCreatedEvent;
import com.albert.commerce.api.order.command.domain.OrderLine;
import com.albert.commerce.api.order.query.application.OrderFacade;
import com.albert.commerce.api.order.query.application.dto.OrderCreatedRequest;
import com.albert.commerce.api.order.query.application.dto.OrderDetailRequest;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderEventHandler {

    private final OrderFacade orderFacade;

    @ServiceActivator(inputChannel = "com.albert.commerce.api.order.command.domain.OrderCreatedEvent")
    public void handleProductCreatedEvent(OrderCreatedEvent orderCreatedEvent) {
        OrderCreatedRequest orderCreatedRequest = toOrderCreatedRequestFrom(orderCreatedEvent);
        orderFacade.save(orderCreatedRequest);
    }

    private static OrderCreatedRequest toOrderCreatedRequestFrom(OrderCreatedEvent orderCreatedEvent) {
        return OrderCreatedRequest.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .userId(orderCreatedEvent.getUserId())
                .storeId(orderCreatedEvent.getStoreId())
                .deliveryStatus(orderCreatedEvent.getDeliveryStatus())
                .orderDetailRequests(toOrderDetailRequestsFrom(orderCreatedEvent.getOrderLines()))
                .createdTime(orderCreatedEvent.getCreatedTime())
                .updateTime(orderCreatedEvent.getUpdateTime())
                .build();
    }

    private static List<OrderDetailRequest> toOrderDetailRequestsFrom(List<OrderLine> orderLines) {
        return orderLines.stream()
                .map(OrderEventHandler::toOrderDetailRequestFrom)
                .toList();
    }

    private static OrderDetailRequest toOrderDetailRequestFrom(OrderLine orderLine) {
        return OrderDetailRequest.builder()
                .productId(orderLine.getProductId())
                .price(orderLine.getPrice())
                .quantity(orderLine.getQuantity())
                .amount(orderLine.getAmount())
                .build();
    }
}
