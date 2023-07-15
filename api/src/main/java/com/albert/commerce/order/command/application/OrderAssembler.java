package com.albert.commerce.order.command.application;

import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.query.application.OrderDetail;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

@Component
public class OrderAssembler extends RepresentationModelAssemblerSupport<Order, OrderDetail> {

    public OrderAssembler() {
        super(OrderService.class, OrderDetail.class);
    }


    @Override
    public OrderDetail toModel(Order entity) {
        return null;
    }
}
