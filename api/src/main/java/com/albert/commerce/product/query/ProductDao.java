package com.albert.commerce.product.query;

import com.albert.commerce.product.command.domain.Product;
import com.albert.commerce.product.command.domain.QProduct;
import com.albert.commerce.store.command.domain.QStore;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.QUser;
import com.albert.commerce.user.command.domain.UserId;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ProductDao {

    private final JPAQueryFactory jpaQueryFactory;

    private static JPQLQuery<UserId> getUserIdByEmail(String userEmail) {
        QUser qUser = QUser.user;
        return JPAExpressions
                .select(qUser.id)
                .from(qUser)
                .where(qUser.email.eq(userEmail));
    }

    private static JPQLQuery<StoreId> getStoreIdByUserId(JPQLQuery<UserId> userIdByEmail) {
        QStore qStore = QStore.store;
        return JPAExpressions
                .select(qStore.storeId)
                .from(qStore)
                .where(qStore.storeUserId.userId.eq(
                        userIdByEmail
                ));
    }

    public Page<Product> findProductsByUserEmail(String userEmail, Pageable pageable) {

        QProduct qProduct = QProduct.product;
        List<Product> contentProducts = jpaQueryFactory.selectFrom(qProduct)
                .where(
                        qProduct.storeId.eq(
                                getStoreIdByUserId(getUserIdByEmail(userEmail))
                        )
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        JPAQuery<Long> productJPAQuery = jpaQueryFactory
                .select(qProduct.count())
                .from(qProduct)
                .where(
                        qProduct.storeId.eq(
                                getStoreIdByUserId(getUserIdByEmail(userEmail))
                        )
                );
        return PageableExecutionUtils.getPage(contentProducts, pageable, productJPAQuery::fetchOne);
    }
}
