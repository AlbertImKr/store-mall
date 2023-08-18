package com.albert.commerce.command.adapter.in.web;

import com.albert.commerce.command.adapter.in.web.dto.OrderCancelRequest;
import com.albert.commerce.command.adapter.in.web.dto.OrderPlaceRequest;
import com.albert.commerce.shared.messaging.application.CommandGateway;
import com.albert.commerce.shared.messaging.application.OrderCancelCommand;
import com.albert.commerce.shared.messaging.application.OrderPlaceCommand;
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
        OrderPlaceCommand orderPlaceCommand = new OrderPlaceCommand(
                userEmail,
                orderPlaceRequest.storeId(),
                orderPlaceRequest.productsIdAndQuantity()
        );
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
        OrderCancelCommand orderCancelCommand = new OrderCancelCommand(
                userEmail,
                storeId,
                orderCancelRequest.description()
        );
        commandGateway.request(orderCancelCommand);
        return ResponseEntity.noContent().build();
    }

}
