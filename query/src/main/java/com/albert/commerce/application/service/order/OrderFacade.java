package com.albert.commerce.application.service.order;

import com.albert.commerce.adapter.out.persistance.Money;
import com.albert.commerce.application.service.order.dto.OrderCanceledEvent;
import com.albert.commerce.application.service.order.dto.OrderDetailRequest;
import com.albert.commerce.application.service.order.dto.OrderPlacedEvent;
import com.albert.commerce.application.service.product.ProductFacade;
import com.albert.commerce.application.service.store.StoreFacade;
import com.albert.commerce.application.service.user.UserFacade;
import com.albert.commerce.domain.order.Order;
import com.albert.commerce.domain.order.OrderDao;
import com.albert.commerce.domain.order.OrderDetail;
import com.albert.commerce.domain.order.OrderDetails;
import com.albert.commerce.domain.product.Product;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.user.User;
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
        var order = orderDao.findById(orderCanceledEvent.orderId())
                .orElseThrow(OrderNotFoundException::new);
        order.cancel(orderCanceledEvent.updatedTime());
    }

    @Transactional
    public void place(OrderPlacedEvent orderPlacedEvent) {
        var user = userFacade.getUserById(orderPlacedEvent.userId());
        var store = storeFacade.getStoreById(orderPlacedEvent.storeId());
        var orderDetails = getOrderDetails(orderPlacedEvent.orderDetailRequests());
        var totalAmount = getTotalAmount(orderDetails);
        var order = toOrder(orderPlacedEvent, user, store, orderDetails, totalAmount);
        orderDao.save(order);
    }

    private List<OrderDetail> getOrderDetails(List<OrderDetailRequest> orderDetailRequests) {
        return orderDetailRequests.stream()
                .map(orderDetailRequest -> {
                            Product product = productFacade.getProductByProductId(orderDetailRequest.productId());
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
                .build();
    }
}
