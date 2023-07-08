package com.albert.commerce.order.command.application;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.order.command.domain.OrderId;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.Links;
import org.springframework.hateoas.RepresentationModel;

@Getter
public class OrderCreatedResponse extends RepresentationModel<OrderCreatedResponse> {

    private final OrderId orderId;

    @Builder
    protected OrderCreatedResponse(OrderId orderId, Links links) {
        this.add(links);
        this.orderId = orderId;
    }

    public static OrderCreatedResponse from(OrderId orderId) {
        return OrderCreatedResponse.builder()
                .orderId(orderId)
                .links(Links.of(BusinessLinks.CREATE_ORDER_LINK, BusinessLinks.getOrder(orderId)))
                .build();
    }
}
