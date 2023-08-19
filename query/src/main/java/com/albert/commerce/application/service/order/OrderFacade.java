package com.albert.commerce.application.service.order;

import com.albert.commerce.adapter.out.persistance.Money;
import com.albert.commerce.application.service.order.dto.OrderCanceledEvent;
import com.albert.commerce.application.service.order.dto.OrderCreatedEvent;
import com.albert.commerce.application.service.order.dto.OrderDetailRequest;
import com.albert.commerce.application.service.product.ProductFacade;
import com.albert.commerce.application.service.store.StoreFacade;
import com.albert.commerce.application.service.user.UserFacade;
import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderDao;
import com.albert.commerce.domain.order.OrderDetail;
import com.albert.commerce.domain.order.OrderDetails;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserId;
import com.albert.commerce.exception.OrderNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Component
public class OrderFacade {

    private final UserFacade userFacade;
    private final OrderDao orderDao;
    private final ProductFacade productFacade;
    private final StoreFacade storeFacade;

    @Transactional
    public void cancel(OrderCanceledEvent orderCanceledEvent) {
        Order order = orderDao.findById(orderCanceledEvent.orderId())
                .orElseThrow(OrderNotFoundException::new);
        order.cancel(orderCanceledEvent.updatedTime());
    }

    private static Money getTotalAmount(List<OrderDetail> orderDetails) {
        return Money.from(orderDetails.stream()
                .mapToLong(orderDetail -> orderDetail.getAmount().value())
                .sum());
    }

    @Transactional
    public void save(OrderCreatedEvent orderCreatedEvent) {
        UserId userId = orderCreatedEvent.userId();
        StoreId storeId = orderCreatedEvent.storeId();
        User user = userFacade.getUserById(userId);
        Store store = storeFacade.getStoreById(storeId);
        List<OrderDetailRequest> orderDetailRequests = orderCreatedEvent.orderDetailRequests();
        List<OrderDetail> orderDetails = getOrderDetails(orderDetailRequests);
        Money totalAmount = getTotalAmount(orderDetails);
        Order order = toOrderEntityOf(orderCreatedEvent, user, store, orderDetails, totalAmount);
        orderDao.save(order);
    }

    private List<OrderDetail> getOrderDetails(List<OrderDetailRequest> orderDetailRequests) {
        return orderDetailRequests.stream()
                .map(orderDetailRequest -> {
                            Product product = productFacade.getByProductId(orderDetailRequest.productId());
                            return OrderDetail.builder()
                                    .productId(product.getProductId())
                                    .productName(product.getProductName())
                                    .quantity(orderDetailRequest.quantity())
                                    .price(orderDetailRequest.price())
                                    .amount(orderDetailRequest.amount())
                                    .productDescription(product.getDescription())
                                    .build();
                        }
                )
                .toList();
    }

    private static Order toOrderEntityOf(OrderCreatedEvent orderCreatedEvent,
            User user, Store store, List<OrderDetail> orderDetails, Money totalAmount) {
        return Order.builder()
                .orderId(orderCreatedEvent.orderId())
                .userId(orderCreatedEvent.userId())
                .orderUserNickName(user.getNickname())
                .storeId(orderCreatedEvent.storeId())
                .storeName(store.getStoreName())
                .orderDetails(new OrderDetails(orderDetails))
                .deliveryStatus(orderCreatedEvent.deliveryStatus())
                .amount(totalAmount)
                .createdTime(orderCreatedEvent.createdTime())
                .build();
    }
}
