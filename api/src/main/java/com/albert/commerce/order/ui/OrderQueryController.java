package com.albert.commerce.order.ui;

import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.query.application.OrderDetail;
import com.albert.commerce.order.query.application.OrderFacade;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/orders", produces = MediaTypes.HAL_JSON_VALUE)
public class OrderQueryController {

    private final OrderFacade orderFacade;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetail> getOrder(Principal principal,
            @PathVariable String orderId) {
        OrderDetail order = orderFacade.findById(OrderId.from(orderId), principal.getName());

        // HATEOAS
        return ResponseEntity.ok(order);
    }

    @GetMapping
    public ResponseEntity<Page<OrderDetail>> getAllOrders(Principal principal,
            Pageable pageable) {
        String userEmail = principal.getName();
        Page<OrderDetail> orders = orderFacade.findAllByUserId(userEmail, pageable);
        // HATEOAS

        return ResponseEntity.ok(orders);
    }
}