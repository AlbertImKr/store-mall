package com.albert.commerce.order.ui;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.common.units.BusinessLinks;
import com.albert.commerce.order.command.application.OrderAssembler;
import com.albert.commerce.order.command.application.OrderCreateResponse;
import com.albert.commerce.order.command.application.OrderRequest;
import com.albert.commerce.order.command.application.OrderResponse;
import com.albert.commerce.order.command.application.OrderResponseEntity;
import com.albert.commerce.order.command.application.OrderService;
import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.query.ProductDao;
import com.albert.commerce.user.query.application.UserInfoResponse;
import com.albert.commerce.user.query.domain.UserDao;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
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
    private final ProductDao productDao;
    private final UserDao userDao;

    private final OrderAssembler orderAssembler;

    private static long getAmount(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .mapToLong(Money::value)
                .sum();
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseEntity> getOrder(Principal principal,
            @PathVariable OrderId orderId) {
        OrderResponse order = orderService.findById(orderId, principal.getName());

        OrderResponseEntity orderResponse = orderAssembler.toModel(order);

        return ResponseEntity.ok(orderResponse);
    }

    @PostMapping
    public ResponseEntity<OrderCreateResponse> createOrder(Principal principal,
            @RequestBody OrderRequest orderRequest) {
        UserInfoResponse user = userDao.findUserProfileByEmail(principal.getName());
        List<Product> products = productDao.findProductsByProductsId(orderRequest.productsId(),
                orderRequest.storeId());
        long amount = getAmount(products);
        Order order = orderService.createOrder(user.getId(), amount, products,
                orderRequest.storeId());

        // HATEOAS
        Link orderLink = BusinessLinks.getOrder(order.getOrderId());
        OrderCreateResponse orderCreateResponse = OrderCreateResponse.from(order.getOrderId());

        return ResponseEntity.created(orderLink.toUri()).body(orderCreateResponse);
    }
}
