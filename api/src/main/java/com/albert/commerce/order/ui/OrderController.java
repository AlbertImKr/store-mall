package com.albert.commerce.order.ui;

import com.albert.commerce.order.command.application.OrderResponse;
import com.albert.commerce.order.command.application.OrderService;
import com.albert.commerce.order.command.domain.OrderId;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/orders", produces = MediaTypes.HAL_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{orderId}")
    public ResponseEntity<EntityModel<OrderResponse>> getOrder(Principal principal,
            @PathVariable OrderId orderId) {
        OrderResponse order = orderService.findById(orderId, principal.getName());
        EntityModel<OrderResponse> model = EntityModel.of(order);
        return ResponseEntity.ok(model);
    }
}
