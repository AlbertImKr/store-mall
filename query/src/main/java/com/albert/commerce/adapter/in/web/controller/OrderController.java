package com.albert.commerce.adapter.in.web.controller;

import com.albert.commerce.adapter.in.web.facade.OrderFacade;
import com.albert.commerce.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {

    private final OrderFacade orderFacade;

    @GetMapping("/orders/{id}")
    public Order getById(@PathVariable String id) {
        return orderFacade.getById(id);
    }

}
