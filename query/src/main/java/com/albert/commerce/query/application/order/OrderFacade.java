package com.albert.commerce.query.application.order;

import com.albert.commerce.query.application.order.dto.OrderCanceledEvent;
import com.albert.commerce.query.application.order.dto.OrderCreatedEvent;
import com.albert.commerce.query.application.order.dto.OrderDetailRequest;
import com.albert.commerce.query.application.product.ProductFacade;
import com.albert.commerce.query.application.store.StoreFacade;
import com.albert.commerce.query.application.user.UserFacade;
import com.albert.commerce.query.common.exception.OrderNotFoundException;
import com.albert.commerce.query.domain.order.OrderDao;
import com.albert.commerce.query.domain.order.OrderData;
import com.albert.commerce.query.domain.order.OrderDetail;
import com.albert.commerce.query.domain.order.OrderDetails;
import com.albert.commerce.query.domain.product.ProductData;
import com.albert.commerce.query.domain.store.StoreData;
import com.albert.commerce.query.domain.store.StoreId;
import com.albert.commerce.query.domain.user.UserData;
import com.albert.commerce.query.domain.user.UserId;
import com.albert.commerce.query.infra.persistance.Money;
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
        OrderData orderData = orderDao.findById(orderCanceledEvent.orderId())
                .orElseThrow(OrderNotFoundException::new);
        orderData.cancel(orderCanceledEvent.updatedTime());
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
        UserData userData = userFacade.getUserById(userId);
        StoreData storeData = storeFacade.getStoreById(storeId);
        List<OrderDetailRequest> orderDetailRequests = orderCreatedEvent.orderDetailRequests();
        List<OrderDetail> orderDetails = getOrderDetails(orderDetailRequests);
        Money totalAmount = getTotalAmount(orderDetails);
        OrderData order = toOrderEntityOf(orderCreatedEvent, userData, storeData, orderDetails, totalAmount);
        orderDao.save(order);
    }

    private List<OrderDetail> getOrderDetails(List<OrderDetailRequest> orderDetailRequests) {
        return orderDetailRequests.stream()
                .map(orderDetailRequest -> {
                            ProductData product = productFacade.getByProductId(orderDetailRequest.getProductId());
                            return OrderDetail.builder()
                                    .productId(product.getProductId())
                                    .productName(product.getProductName())
                                    .quantity(orderDetailRequest.getQuantity())
                                    .price(orderDetailRequest.getPrice())
                                    .amount(orderDetailRequest.getAmount())
                                    .productDescription(product.getDescription())
                                    .build();
                        }
                )
                .toList();
    }

    private static OrderData toOrderEntityOf(OrderCreatedEvent orderCreatedEvent,
            UserData userData, StoreData storeData, List<OrderDetail> orderDetails, Money totalAmount) {
        return OrderData.builder()
                .orderId(orderCreatedEvent.orderId())
                .userId(orderCreatedEvent.userId())
                .orderUserNickName(userData.getNickname())
                .storeId(orderCreatedEvent.storeId())
                .storeName(storeData.getStoreName())
                .orderDetails(new OrderDetails(orderDetails))
                .deliveryStatus(orderCreatedEvent.deliveryStatus())
                .amount(totalAmount)
                .createdTime(orderCreatedEvent.createdTime())
                .build();
    }
}
