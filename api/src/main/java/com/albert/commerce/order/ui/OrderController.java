package com.albert.commerce.order.ui;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.order.command.application.DeleteOrderRequest;
import com.albert.commerce.order.command.application.OrderCreatedResponse;
import com.albert.commerce.order.command.application.OrderRequest;
import com.albert.commerce.order.command.application.OrderService;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.query.application.OrderDetail;
import com.albert.commerce.order.query.application.OrderFacade;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/orders", produces = MediaTypes.HAL_JSON_VALUE)
public class OrderController {

    private final OrderService orderService;
    private final OrderFacade orderFacade;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetail> getOrder(Principal principal,
            @PathVariable String orderId) {
        OrderDetail order = orderFacade.findById(OrderId.from(orderId), principal.getName());

        // HATEOAS
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<OrderCreatedResponse> placeOrder(Principal principal,
            @RequestBody OrderRequest orderRequest) {
        String userEmail = principal.getName();
        OrderId orderId = orderService.placeOrder(userEmail, orderRequest);

        // HATEOAS
        Link orderLink = BusinessLinks.getOrder(orderId);
        OrderCreatedResponse orderCreatedResponse = OrderCreatedResponse.from(orderId);

        return ResponseEntity.created(orderLink.toUri()).body(orderCreatedResponse);
    }


    @DeleteMapping
    public ResponseEntity<Void> cancelOrder(Principal principal,
            @RequestBody DeleteOrderRequest deleteOrderRequest) {
        String userEmail = principal.getName();
        orderService.cancelOrder(deleteOrderRequest, userEmail);
        return ResponseEntity.noContent().build();
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
