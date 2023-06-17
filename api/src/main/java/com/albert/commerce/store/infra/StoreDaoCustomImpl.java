package com.albert.commerce.store.infra;

import com.albert.commerce.store.command.domain.QStore;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.ui.StoreNotFoundException;
import com.albert.commerce.user.command.domain.QUser;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StoreDaoCustomImpl implements StoreDaoCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public Store findStoreByUserEmail(String email) {
        QUser user = QUser.user;
        QStore store = QStore.store;
        Store targetStore = jpaQueryFactory
                .select(store)
                .from(store)
                .where(store.storeUserId.userId.id.eq(
                        JPAExpressions
                                .select(user.id.id)
                                .from(user)
                                .where(user.email.eq(email))
                )).fetchOne();
        if (targetStore == null) {
            throw new StoreNotFoundException();
        }
        return targetStore;
    }

    public StoreId findStoreIdByUserEmail(String email) {
        QUser user = QUser.user;
        QStore store = QStore.store;
        StoreId storeId = jpaQueryFactory
                .select(store.storeId)
                .from(store)
                .where(store.storeUserId.userId.id.eq(
                        JPAExpressions
                                .select(user.id.id)
                                .from(user)
                                .where(user.email.eq(email))
                )).fetchOne();

        if (storeId == null) {
            throw new StoreNotFoundException();
        }
        return storeId;
    }


}
