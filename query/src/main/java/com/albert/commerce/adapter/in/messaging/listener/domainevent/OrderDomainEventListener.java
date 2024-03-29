package com.albert.commerce.adapter.in.messaging.listener.domainevent;

import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.OrderCanceledEvent;
import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.OrderDetailRequest;
import com.albert.commerce.adapter.in.messaging.listener.domainevent.dto.OrderPlacedEvent;
import com.albert.commerce.adapter.out.config.cache.CacheValue;
import com.albert.commerce.adapter.out.persistence.Money;
import com.albert.commerce.adapter.out.persistence.imports.OrderJpaRepository;
import com.albert.commerce.adapter.out.persistence.imports.ProductJpaRepository;
import com.albert.commerce.adapter.out.persistence.imports.StoreJpaRepository;
import com.albert.commerce.adapter.out.persistence.imports.UserJpaRepository;
import com.albert.commerce.application.service.exception.error.OrderNotFoundException;
import com.albert.commerce.application.service.exception.error.ProductNotFoundException;
import com.albert.commerce.application.service.exception.error.StoreNotFoundException;
import com.albert.commerce.application.service.exception.error.UserNotFoundException;
import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderDetail;
import com.albert.commerce.domain.order.OrderDetails;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.units.DomainEventChannelNames;
import com.albert.commerce.domain.user.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderDomainEventListener {

    private final UserJpaRepository userJpaRepository;
    private final OrderJpaRepository orderJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final StoreJpaRepository storeJpaRepository;

    @ServiceActivator(inputChannel = DomainEventChannelNames.ORDER_PLACED_EVENT)
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

    @CacheEvict(value = CacheValue.ORDER, key = "#orderCanceledEvent.orderId().value")
    @ServiceActivator(inputChannel = DomainEventChannelNames.ORDER_CANCELED_EVENT)
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
