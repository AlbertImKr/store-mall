package com.albert.commerce.order.infra.persistence;

import com.albert.commerce.order.command.domain.Order;
import com.albert.commerce.order.command.domain.OrderId;
import com.albert.commerce.order.command.domain.QOrder;
import com.albert.commerce.order.query.domain.OrderDao;
import com.albert.commerce.user.command.domain.QUser;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
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
}
