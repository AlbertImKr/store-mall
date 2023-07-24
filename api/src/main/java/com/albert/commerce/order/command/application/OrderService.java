package com.albert.commerce.order.command.application;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.command.domain.OrderLine;
import com.albert.commerce.order.command.domain.OrderRepository;
import com.albert.commerce.product.ProductNotFoundException;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.domain.ProductDao;
import com.albert.commerce.product.query.domain.ProductData;
import com.albert.commerce.store.StoreNotFoundException;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.query.domain.StoreDataDao;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.domain.UserId;
import com.albert.commerce.user.query.domain.UserDao;
import com.albert.commerce.user.query.domain.UserData;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserDao userDao;
    private final StoreDataDao storeDataDao;
    private final ProductDao productDao;

    @Transactional
    public void cancelOrder(DeleteOrderRequest deleteOrderRequest, String userEmail) {
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        OrderId orderId = OrderId.from(deleteOrderRequest.orderId());
        checkOrder(orderId, user.getUserId());
        orderRepository.deleteById(orderId);
    }

    private void checkOrder(OrderId orderId, UserId userId) {
        if (!orderRepository.exist(orderId, userId)) {
            throw new OrderNotFoundException();
        }
    }

    @Transactional
    public OrderId placeOrder(String userEmail, OrderRequest orderRequest) {
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        StoreId storeId = StoreId.from(orderRequest.storeId());
        if (!storeDataDao.exists(storeId)) {
            throw new StoreNotFoundException();
        }
        Map<String, Long> productsIdAndQuantity = orderRequest.productsIdAndQuantity();
        List<ProductData> products = productsIdAndQuantity.keySet().stream()
                .map(productId -> productDao.findById(ProductId.from(productId))
                        .orElseThrow(ProductNotFoundException::new))
                .toList();
        Order order = Order.builder()
                .storeId(storeId)
                .userId(user.getUserId())
                .orderLines(
                        products.stream()
                                .map(productData -> {
                                            Money price = productData.getPrice();
                                            Long quantity = productsIdAndQuantity.get(productData.getProductId().getId());
                                            Money amount = price.multiply(quantity);
                                            return OrderLine.builder()
                                                    .productId(productData.getProductId())
                                                    .price(price)
                                                    .quantity(quantity)
                                                    .amount(amount)
                                                    .build();
                                        }
                                )
                                .toList()
                )
                .build();
        return orderRepository.save(order).getOrderId();
    }
}
