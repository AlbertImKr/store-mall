package com.albert.commerce.order.command.application;

import com.albert.commerce.common.infra.persistence.Money;
import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.command.domain.OrderRepository;
import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.ProductId;
import com.albert.commerce.product.query.ProductDao;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.domain.UserDao;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductDao productDao;
    private final UserDao userDao;

    private static long getAmount(List<Product> products) {
        return products.stream()
                .map(Product::getPrice)
                .mapToLong(Money::value)
                .sum();
    }

    @Transactional
    public void deleteOrder(DeleteOrderRequest deleteOrderRequest, String email) {
        User user = userDao.findUserByEmail(email);
        OrderId orderId = OrderId.from(deleteOrderRequest.orderId());
        checkOrder(orderId, user);
        orderRepository.deleteById(orderId);
    }

    private void checkOrder(OrderId orderId, User user) {
        if (!orderRepository.exist(orderId, user.getId())) {
            throw new OrderNotFoundException();
        }
    }

    @Transactional
    public Order createOrder(String email, OrderRequest orderRequest) {
        User user = userDao.findUserByEmail(email);
        StoreId storeId = StoreId.from(orderRequest.storeId());
        List<ProductId> productsId = orderRequest.productsId().stream()
                .map(ProductId::from)
                .collect(Collectors.toList());
        List<Product> products = productDao.findProductsByProductsId(
                productsId,
                storeId);
        long amount = getAmount(products);
        Order order = Order.builder()
                .orderId(orderRepository.nextId())
                .userId(user.getId())
                .storeId(storeId)
                .productsId(productsId)
                .amount(amount)
                .build();
        return orderRepository.save(order);
    }


}
