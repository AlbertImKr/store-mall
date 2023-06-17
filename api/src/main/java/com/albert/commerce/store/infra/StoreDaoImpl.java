package com.albert.commerce.store.infra;

import com.albert.commerce.store.command.domain.QStore;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.QUser;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StoreDaoImpl implements StoreDaoCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public Optional<StoreId> findStoreIdByUserEmail(String email) {
        QUser user = QUser.user;
        QStore store = QStore.store;
        return Optional.ofNullable(jpaQueryFactory
                .select(store.storeId)
                .from(store)
                .where(store.storeUserId.userId.id.eq(
                        JPAExpressions
                                .select(user.id.id)
                                .from(user)
                                .where(user.email.eq(email))
                )).fetchOne());
    }
}
