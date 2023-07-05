package com.albert.commerce.order.infra.persistence;

import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.command.domain.QOrder;
import com.albert.commerce.order.query.application.OrderDetail;
import com.albert.commerce.order.query.application.QOrderDetail;
import com.albert.commerce.order.query.domain.OrderDao;
import com.albert.commerce.product.command.domain.QProduct;
import com.albert.commerce.user.command.domain.QUser;
import com.albert.commerce.user.command.domain.UserId;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public OrderDetail findById(OrderId orderId, String userEmail) {
        QOrder qOrder = QOrder.order;
        QUser qUser = QUser.user;
        QProduct qProduct = QProduct.product;
        Map<String, OrderDetail> fetch = jpaQueryFactory
                .select(qOrder.orderId,
                        qOrder.userId,
                        qOrder.deliveryStatus,
                        qOrder.amount,
                        qOrder.createdTime,
                        qOrder.storeId,
                        qProduct)
                .from(qOrder)
                .leftJoin(qProduct).on(qOrder.productsId.contains(qProduct.productId))
                .where(qOrder.orderId.eq(orderId)
                        .and(qOrder.userId.eq(
                                JPAExpressions
                                        .select(qUser.id)
                                        .from(qUser)
                                        .where(qUser.email.eq(userEmail))
                        )))
                .transform(
                        groupBy(qOrder.orderId.id).as(
                                new QOrderDetail(
                                        qOrder.orderId,
                                        qOrder.userId,
                                        list(qProduct),
                                        qOrder.deliveryStatus,
                                        qOrder.amount,
                                        qOrder.createdTime,
                                        qOrder.storeId
                                )
                        )
                );

        return fetch.get(orderId.getId());
    }

    @Override
    public Page<OrderDetail> findByUserId(UserId userId, Pageable pageable) {
        QOrder qOrder = QOrder.order;
        QProduct qProduct = QProduct.product;

        JPQLQuery<Order> limitOrders = JPAExpressions
                .select(qOrder)
                .from(qOrder)
                .where(qOrder.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());

        Map<OrderId, OrderDetail> orderDetailMap = jpaQueryFactory
                .from(qOrder)
                .leftJoin(qProduct).on(qOrder.productsId.contains(qProduct.productId))
                .where(limitOrders.contains(qOrder))
                .transform(groupBy(qOrder.orderId).as(
                                new QOrderDetail(
                                        qOrder.orderId,
                                        qOrder.userId,
                                        list(qProduct),
                                        qOrder.deliveryStatus,
                                        qOrder.amount,
                                        qOrder.createdTime,
                                        qOrder.storeId
                                )
                        )
                );

//        Map<OrderId, OrderDetail> orders = jpaQueryFactory
//                .from(qOrder)
//                .leftJoin(qProduct).on(qOrder.productsId.contains(qProduct.productId))
//                .where(qOrder.userId.eq(userId))
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .transform(groupBy(qOrder.orderId).as(
//                                new QOrderDetail(
//                                        qOrder.orderId,
//                                        qOrder.userId,
//                                        list(qProduct),
//                                        qOrder.deliveryStatus,
//                                        qOrder.amount,
//                                        qOrder.createdTime,
//                                        qOrder.storeId
//                                )
//                        )
//                );

        JPAQuery<Long> productJPAQuery = jpaQueryFactory
                .select(qOrder.count())
                .from(qOrder)
                .where(
                        qOrder.userId.eq(
                                userId
                        )
                );
        return PageableExecutionUtils.getPage(orderDetailMap.values().stream().toList(), pageable,
                productJPAQuery::fetchOne);
    }

}
