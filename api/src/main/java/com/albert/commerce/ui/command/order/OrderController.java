package com.albert.commerce.ui.command.order;

import com.albert.commerce.application.command.order.DeleteOrderRequest;
import com.albert.commerce.application.command.order.OrderRequest;
import com.albert.commerce.application.command.order.OrderService;
import com.albert.commerce.domain.command.order.OrderId;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/orders", produces = MediaTypes.HAL_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Map<String, String>> placeOrder(Principal principal,
            @RequestBody OrderRequest orderRequest) {
        String userEmail = principal.getName();
        OrderId orderId = orderService.placeOrder(userEmail, orderRequest);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("orderId", orderId.getId()));
    }


    @DeleteMapping
    public ResponseEntity<Void> cancelOrder(Principal principal,
            @RequestBody DeleteOrderRequest deleteOrderRequest) {
        String userEmail = principal.getName();
        orderService.cancelOrder(deleteOrderRequest, userEmail);
        return ResponseEntity.noContent().build();
    }

}
