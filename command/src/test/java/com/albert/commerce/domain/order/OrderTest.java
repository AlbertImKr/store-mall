package com.albert.commerce.domain.order;

import static com.albert.commerce.domain.DomainFixture.createOrder;
import static com.albert.commerce.domain.DomainFixture.getExpectedOrderDetailRequests;
import static org.assertj.core.api.Assertions.assertThat;

import com.albert.commerce.domain.DomainFixture;
import com.albert.commerce.domain.event.DomainEvent;
import com.albert.commerce.domain.event.Events;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OrderTest {

    @DisplayName("주문이 생성시 주문 생성 이벤트가 발행한다")
    @Test
    void when_order_create_then_order_created_event_published() {
        // given
        OrderId orderId = DomainFixture.getOrderId();
        StoreId storeId = DomainFixture.getStoreId();
        Map<String, Long> productsIdAndQuantity = DomainFixture.getProductsIdAndQuantity();
        List<Product> products = DomainFixture.getProducts();
        LocalDateTime createdTime = DomainFixture.getCreatedTime();
        UserId userId = DomainFixture.getUserId();
        Events.clear();
        List<OrderDetailRequest> orderDetailRequests = getExpectedOrderDetailRequests();

        // when
        Order.from(
                orderId,
                storeId,
                productsIdAndQuantity,
                products,
                createdTime,
                userId
        );

        // then
        checkPlaceOrderEvent(orderId, userId, storeId, orderDetailRequests, createdTime);
    }

    @DisplayName("주문 취소시 주문 취소 이벤트가 발행한다")
    @Test
    void when_cancel_order_then_order_cancel_event_published() {
        // given
        OrderId orderId = DomainFixture.getOrderId();
        LocalDateTime createdTime = DomainFixture.getCreatedTime();
        Order order = createOrder(orderId, createdTime);
        Events.clear();

        // when
        LocalDateTime updatedTime = DomainFixture.getUpdatedTime();
        order.cancel(updatedTime);

        // then
        checkCancelOrderEvent(orderId, updatedTime);
    }

    private void checkCancelOrderEvent(OrderId orderId, LocalDateTime createdTime) {
        List<DomainEvent> events = Events.getEvents();
        DomainEvent domainEvent = events.get(0);
        Assertions.assertAll(
                () -> assertThat(domainEvent.getClass()).isEqualTo(OrderCanceledEvent.class),
                () -> assertThat(((OrderCanceledEvent) domainEvent).getOrderId())
                        .isEqualTo(orderId),
                () -> assertThat(((OrderCanceledEvent) domainEvent).getUpdatedTime())
                        .isEqualTo(createdTime)
        );
    }

    private static void checkPlaceOrderEvent(OrderId orderId, UserId userId, StoreId storeId,
            List<OrderDetailRequest> orderDetailRequests, LocalDateTime createdTime) {
        List<DomainEvent> events = Events.getEvents();
        DomainEvent domainEvent = events.get(0);
        Assertions.assertAll(
                () -> assertThat(domainEvent.getClass()).isEqualTo(OrderPlacedEvent.class),
                () -> assertThat(((OrderPlacedEvent) domainEvent).getOrderId())
                        .isEqualTo(orderId),
                () -> assertThat(((OrderPlacedEvent) domainEvent).getUserId())
                        .isEqualTo(userId),
                () -> assertThat(((OrderPlacedEvent) domainEvent).getStoreId())
                        .isEqualTo(storeId),
                () -> assertThat(((OrderPlacedEvent) domainEvent).getOrderDetailRequests())
                        .usingRecursiveAssertion()
                        .isEqualTo(orderDetailRequests),
                () -> assertThat(((OrderPlacedEvent) domainEvent).getCreatedTime())
                        .isEqualTo(createdTime)
        );
    }
}
