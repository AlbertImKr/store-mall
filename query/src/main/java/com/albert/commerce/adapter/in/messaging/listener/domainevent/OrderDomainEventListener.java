package com.albert.commerce.adapter.in.messaging.listener.domainevent;

import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.OrderCanceledEvent;
import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.OrderDetailRequest;
import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.OrderPlacedEvent;
import com.albert.commerce.adapter.out.persistance.imports.OrderJpaRepository;
import com.albert.commerce.adapter.out.persistance.imports.ProductJpaRepository;
import com.albert.commerce.adapter.out.persistance.imports.StoreJpaRepository;
import com.albert.commerce.adapter.out.persistance.imports.UserJpaRepository;
import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderDetail;
import com.albert.commerce.domain.order.OrderDetails;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.exception.error.OrderNotFoundException;
import com.albert.commerce.exception.error.ProductNotFoundException;
import com.albert.commerce.exception.error.StoreNotFoundException;
import com.albert.commerce.exception.error.UserNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderDomainEventListener {

    private final UserJpaRepository userJpaRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final StoreJpaRepository storeJpaRepository;

    @KafkaListener(topics = "OrderPlacedEvent")
    public void handleOrderPlacedEvent(OrderPlacedEvent orderPlacedEvent) {
        var user = userJpaRepository.findById(orderPlacedEvent.userId())
                .orElseThrow(UserNotFoundException::new);
        var store = storeJpaRepository.findById(orderPlacedEvent.storeId())
                .orElseThrow(StoreNotFoundException::new);
        var orderDetails = getOrderDetails(orderPlacedEvent.orderDetailRequests());
        var totalAmount = getTotalAmount(orderDetails);
        var order = toOrder(orderPlacedEvent, user, store, orderDetails, totalAmount);
        orderJpaRepository.save(order);
    }

    @KafkaListener(topics = "OrderCanceledEvent")
    public void handleOrderCanceledEvent(OrderCanceledEvent orderCanceledEvent) {
        var order = orderJpaRepository.findById(orderCanceledEvent.orderId())
                .orElseThrow(OrderNotFoundException::new);
        order.cancel(orderCanceledEvent.updatedTime());
    }

    private List<OrderDetail> getOrderDetails(List<OrderDetailRequest> orderDetailRequests) {
        return orderDetailRequests.stream()
                .map(orderDetailRequest -> {
                            Product product = productJpaRepository.findById(orderDetailRequest.productId())
                                    .orElseThrow(ProductNotFoundException::new);
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

    private static Money getTotalAmount(List<OrderDetail> orderDetails) {
        return Money.from(orderDetails.stream()
                .mapToLong(orderDetail -> orderDetail.getAmount().value())
                .sum());
    }

    private static Order toOrder(OrderPlacedEvent orderPlacedEvent,
            User user, Store store, List<OrderDetail> orderDetails, Money totalAmount) {
        return Order.builder()
                .orderId(orderPlacedEvent.orderId())
                .userId(orderPlacedEvent.userId())
                .orderUserNickName(user.getNickname())
                .storeId(orderPlacedEvent.storeId())
                .storeName(store.getStoreName())
                .orderDetails(new OrderDetails(orderDetails))
                .deliveryStatus(orderPlacedEvent.deliveryStatus())
                .amount(totalAmount)
                .createdTime(orderPlacedEvent.createdTime())
                .updatedTime(orderPlacedEvent.updatedTime())
                .build();
    }
}
