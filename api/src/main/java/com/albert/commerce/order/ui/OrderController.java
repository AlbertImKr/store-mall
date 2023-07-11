package com.albert.commerce.order.ui;

import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.order.command.application.DeleteOrderRequest;
import com.albert.commerce.order.command.application.OrderAssembler;
import com.albert.commerce.order.command.application.OrderCreatedResponse;
import com.albert.commerce.order.command.application.OrderRequest;
import com.albert.commerce.order.command.application.OrderResponseEntity;
import com.albert.commerce.order.command.application.OrderService;
import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.query.application.OrderDetail;
import com.albert.commerce.order.query.application.OrderFacade;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.application.ProductFacade;
import com.albert.commerce.store.StoreNotFoundException;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.query.domain.StoreDataDao;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.query.domain.UserDao;
import com.albert.commerce.user.query.domain.UserData;
import java.security.Principal;
import java.util.List;
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
    private final StoreDataDao storeDataDao;
    private final UserDao userDao;
    private final ProductFacade productFacade;
    private final PagedResourcesAssembler<OrderDetail> pagedResourcesAssembler;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseEntity> getOrder(Principal principal,
            @PathVariable String orderId) {
        OrderDetail order = orderFacade.findById(OrderId.from(orderId), principal.getName());

        // HATEOAS
        OrderResponseEntity orderResponse = orderAssembler.toModel(order);

        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping
    public ResponseEntity<OrderCreatedResponse> placeOrder(Principal principal,
            @RequestBody OrderRequest orderRequest) {
        String email = principal.getName();
        UserData user = userDao.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
        List<ProductId> productsId = orderRequest.productsId().stream()
                .map(ProductId::from)
                .toList();
        long amount = productFacade.getAmount(productsId);
        StoreId storeId = StoreId.from(orderRequest.storeId());
        if (!storeDataDao.exists(storeId)) {
            throw new StoreNotFoundException();
        }
        Order order = orderService.placeOrder(user.getUserId(), storeId, productsId, amount);

        // HATEOAS
        Link orderLink = BusinessLinks.getOrder(order.getOrderId());
        OrderCreatedResponse orderCreatedResponse = OrderCreatedResponse.from(order.getOrderId());

        return ResponseEntity.created(orderLink.toUri()).body(orderCreatedResponse);
    }


    @DeleteMapping
    public ResponseEntity<Void> cancelOrder(Principal principal,
            @RequestBody DeleteOrderRequest deleteOrderRequest) {
        String userEmail = principal.getName();
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        orderService.cancelOrder(deleteOrderRequest, user.getUserId());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PagedModel<OrderResponseEntity>> getAllOrders(Principal principal,
            Pageable pageable) {
        String userEmail = principal.getName();
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Page<OrderDetail> orders = orderFacade.findAllByUserId(user.getUserId(), pageable);

        // HATEOAS
        PagedModel<OrderResponseEntity> entityModels = pagedResourcesAssembler.toModel(orders,
                orderAssembler);

        return ResponseEntity.ok(entityModels);
    }
}
