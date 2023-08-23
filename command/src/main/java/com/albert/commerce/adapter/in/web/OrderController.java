package com.albert.commerce.adapter.in.web;

import com.albert.commerce.adapter.in.web.request.OrderCancelRequest;
import com.albert.commerce.adapter.in.web.request.OrderPlaceRequest;
import com.albert.commerce.application.port.in.CommandGateway;
import com.albert.commerce.application.service.order.OrderCancelCommand;
import com.albert.commerce.application.service.order.OrderPlaceCommand;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/orders", produces = MediaTypes.HAL_JSON_VALUE)
public class OrderController {

    private final CommandGateway commandGateway;

    @PostMapping
    public ResponseEntity<Map<String, String>> place(
            Principal principal,
            @RequestBody OrderPlaceRequest orderPlaceRequest
    ) {
        String userEmail = principal.getName();
        var orderPlaceCommand = toOrderPlaceCommand(orderPlaceRequest, userEmail);
        String orderId = commandGateway.request(orderPlaceCommand);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("orderId", orderId));
    }


    @DeleteMapping("/{storeId}")
    public ResponseEntity<Void> cancel(
            Principal principal,
            @PathVariable String storeId,
            @RequestBody(required = false) OrderCancelRequest orderCancelRequest
    ) {
        String userEmail = principal.getName();
        var orderCancelCommand = toOrderCancelCommand(storeId, orderCancelRequest, userEmail);
        commandGateway.request(orderCancelCommand);
        return ResponseEntity.noContent().build();
    }

    private static OrderPlaceCommand toOrderPlaceCommand(OrderPlaceRequest orderPlaceRequest, String userEmail) {
        return new OrderPlaceCommand(
                userEmail,
                orderPlaceRequest.storeId(),
                orderPlaceRequest.productsIdAndQuantity()
        );
    }

    private static OrderCancelCommand toOrderCancelCommand(String storeId, OrderCancelRequest orderCancelRequest,
            String userEmail) {
        return new OrderCancelCommand(
                userEmail,
                storeId,
                orderCancelRequest.description()
        );
    }
}
