package com.albert.commerce.order.query.application;

import com.albert.commerce.order.command.application.OrderAssembler;
import com.albert.commerce.order.command.application.OrderNotFoundException;
import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.command.domain.OrderRepository;
import com.albert.commerce.order.query.domain.OrderDao;
import com.albert.commerce.product.ProductNotFoundException;
import com.albert.commerce.product.query.domain.ProductDao;
import com.albert.commerce.product.query.domain.ProductData;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.query.domain.UserDao;
import com.albert.commerce.user.query.domain.UserData;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Component
public class OrderFacade {

    private final OrderDao orderDao;
    private final UserDao userDao;
    private final OrderRepository orderRepository;
    private final ProductDao productDao;
    private final PagedResourcesAssembler<Order> pagedResourcesAssembler;
    private final OrderAssembler orderAssembler;


    @Transactional(readOnly = true)
    public OrderDetail findById(OrderId orderId, String userEmail) {
        UserData userData = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Order order = orderRepository.findByUserIdAndOrderId(userData.getUserId(), orderId)
                .orElseThrow(OrderNotFoundException::new);

        List<OrderLineDetail> orderLineDetails = getOrderLineDetails(order);
        return OrderDetail.of(order, orderLineDetails);
    }

    private List<OrderLineDetail> getOrderLineDetails(Order order) {
        return order.getOrderLines().stream()
                .map(orderLine -> {
                    ProductData productData = productDao.findById(orderLine.getProductId())
                            .orElseThrow(ProductNotFoundException::new);
                    return OrderLineDetail.of(orderLine, productData);
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public Page<OrderDetail> findAllByUserId(String userEmail, Pageable pageable) {
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Page<Order> orders = orderRepository.findAllByUserId(user.getUserId(), pageable);
        List<Order> ordersContent = orders.getContent();
        List<OrderDetail> orderDetails = ordersContent.stream()
                .map(order -> OrderDetail.of(order, getOrderLineDetails(order)))
                .toList();
        return new PageImpl<>(orderDetails, orders.getPageable(), orders.getTotalElements());
    }
}
