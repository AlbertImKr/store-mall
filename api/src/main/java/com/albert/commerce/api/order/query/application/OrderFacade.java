package com.albert.commerce.api.order.query.application;

import com.albert.commerce.api.order.query.application.dto.OrderCreatedRequest;
import com.albert.commerce.api.order.query.application.dto.OrderDetailRequest;
import com.albert.commerce.api.order.query.domain.OrderDao;
import com.albert.commerce.api.order.query.domain.OrderData;
import com.albert.commerce.api.order.query.domain.OrderDetail;
import com.albert.commerce.api.order.query.domain.OrderDetails;
import com.albert.commerce.api.product.command.application.dto.ProductResponse;
import com.albert.commerce.api.product.query.application.ProductFacade;
import com.albert.commerce.api.store.query.application.StoreFacade;
import com.albert.commerce.api.store.query.domain.StoreData;
import com.albert.commerce.api.user.query.application.UserFacade;
import com.albert.commerce.api.user.query.domain.UserData;
import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.infra.persistence.Money;
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

    private static Money getTotalAmount(List<OrderDetail> orderDetails) {
        return Money.from(orderDetails.stream()
                .mapToLong(orderDetail -> orderDetail.getAmount().value())
                .sum());
    }

    private static OrderData toOrderEntityOf(OrderCreatedRequest orderCreatedRequest,
            UserData userData, StoreData storeData, List<OrderDetail> orderDetails, Money totalAmount) {
        return OrderData.builder()
                .orderId(orderCreatedRequest.getOrderId())
                .userId(orderCreatedRequest.getUserId())
                .orderUserNickName(userData.getNickname())
                .storeId(orderCreatedRequest.getStoreId())
                .storeName(storeData.getStoreName())
                .orderDetails(new OrderDetails(orderDetails))
                .deliveryStatus(orderCreatedRequest.getDeliveryStatus())
                .amount(totalAmount)
                .createdTime(orderCreatedRequest.getCreatedTime())
                .build();
    }

    @Transactional
    public void save(OrderCreatedRequest orderCreatedRequest) {
        DomainId userId = orderCreatedRequest.getUserId();
        DomainId storeId = orderCreatedRequest.getStoreId();
        UserData userData = userFacade.getUserById(userId);
        StoreData storeData = storeFacade.getStoreById(storeId);
        List<OrderDetailRequest> orderDetailRequests = orderCreatedRequest.getOrderDetailRequests();
        List<OrderDetail> orderDetails = getOrderDetails(orderDetailRequests);
        Money totalAmount = getTotalAmount(orderDetails);
        OrderData order = toOrderEntityOf(orderCreatedRequest, userData, storeData, orderDetails, totalAmount);
        orderDao.save(order);
    }

    private List<OrderDetail> getOrderDetails(List<OrderDetailRequest> orderDetailRequests) {
        return orderDetailRequests.stream()
                .map(orderDetailRequest -> {
                            ProductResponse product = productFacade.findById(orderDetailRequest.getProductId());
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
}
