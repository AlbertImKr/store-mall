package com.albert.commerce.order.command.application;

import com.albert.commerce.order.ui.OrderController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class OrderAssembler extends
        RepresentationModelAssemblerSupport<OrderResponse, OrderResponseEntity> {

    public OrderAssembler() {
        super(OrderController.class, OrderResponseEntity.class);
    }

    @Override
    public OrderResponseEntity toModel(OrderResponse orderResponse) {
        return OrderResponseEntity.from(orderResponse);
    }
}
