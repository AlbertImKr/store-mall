package com.albert.commerce.order.ui;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.order.command.application.DeleteOrderRequest;
import com.albert.commerce.order.command.application.OrderAssembler;
import com.albert.commerce.order.command.application.OrderCreateResponse;
import com.albert.commerce.order.command.application.OrderRequest;
import com.albert.commerce.order.command.application.OrderResponseEntity;
import com.albert.commerce.order.command.application.OrderService;
import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.query.application.OrderDetail;
import com.albert.commerce.order.query.application.OrderFacade;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.PagedModel;
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
    private final OrderAssembler orderAssembler;
    private final PagedResourcesAssembler<OrderDetail> pagedResourcesAssembler;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseEntity> getOrder(Principal principal,
            @PathVariable OrderId orderId) {
        OrderDetail order = orderFacade.findById(orderId, principal.getName());

        // HATEOAS
        OrderResponseEntity orderResponse = orderAssembler.toModel(order);

        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping
    public ResponseEntity<OrderCreateResponse> createOrder(Principal principal,
            @RequestBody OrderRequest orderRequest) {
        String email = principal.getName();
        Order order = orderService.createOrder(email, orderRequest);

        // HATEOAS
        Link orderLink = BusinessLinks.getOrder(order.getOrderId());
        OrderCreateResponse orderCreateResponse = OrderCreateResponse.from(order.getOrderId());

        return ResponseEntity.created(orderLink.toUri()).body(orderCreateResponse);
    }


    @DeleteMapping
    public ResponseEntity<Void> deleteOrder(Principal principal,
            @RequestBody DeleteOrderRequest deleteOrderRequest) {
        orderService.deleteOrder(deleteOrderRequest, principal.getName());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PagedModel<OrderResponseEntity>> getAllOrders(Principal principal,
            Pageable pageable) {
        Page<OrderDetail> orders = orderFacade.findAllByUserId(principal.getName(),
                pageable);

        // HATEOAS
        PagedModel<OrderResponseEntity> entityModels = pagedResourcesAssembler.toModel(orders,
                orderAssembler);

        return ResponseEntity.ok(entityModels);
    }
}
