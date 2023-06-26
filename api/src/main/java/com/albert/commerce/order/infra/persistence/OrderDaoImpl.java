package com.albert.commerce.order.infra.persistence;

import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.command.domain.QOrder;
import com.albert.commerce.order.query.application.OrderDetail;
import com.albert.commerce.order.query.domain.OrderDao;
import com.albert.commerce.user.command.domain.QUser;
import com.albert.commerce.user.command.domain.UserId;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.stream.Collectors;
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
    public Order findById(OrderId orderId, String userEmail) {
        QOrder qOrder = QOrder.order;
        QUser qUser = QUser.user;

        return jpaQueryFactory
                .select(qOrder)
                .from(qOrder)
                .where(qOrder.orderId.eq(orderId)
                        .and(qOrder.userId.eq(
                                JPAExpressions
                                        .select(qUser.id)
                                        .from(qUser)
                                        .where(qUser.email.eq(userEmail))
                        ))
                )
                .fetchFirst();
    }

    @Override
    public Page<OrderDetail> findByUserId(UserId userId, Pageable pageable) {
        QOrder qOrder = QOrder.order;
        List<Order> orders = jpaQueryFactory
                .select(qOrder)
                .from(qOrder)
                .where(qOrder.userId.eq(userId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        List<OrderDetail> orderDetails = orders.stream()
                .map(OrderDetail::from)
                .collect(Collectors.toList());
        JPAQuery<Long> productJPAQuery = jpaQueryFactory
                .select(qOrder.count())
                .from(qOrder)
                .where(
                        qOrder.userId.eq(
                                userId
                        )
                );
        return PageableExecutionUtils.getPage(orderDetails, pageable, productJPAQuery::fetchOne);
    }
}
