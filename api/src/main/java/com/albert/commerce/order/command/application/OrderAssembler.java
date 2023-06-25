package com.albert.commerce.order.command.application;

import com.albert.commerce.order.query.application.OrderDetail;
import com.albert.commerce.order.ui.OrderController;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class OrderAssembler extends
        RepresentationModelAssemblerSupport<OrderDetail, OrderResponseEntity> {

    public OrderAssembler() {
        super(OrderController.class, OrderResponseEntity.class);
    }

    @Override
    public OrderResponseEntity toModel(OrderDetail orderDetail) {
        return OrderResponseEntity.from(orderDetail);
    }
}
