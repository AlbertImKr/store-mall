package com.albert.commerce.store.query;

import com.albert.commerce.store.command.domain.QStore;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreUserId;
import com.albert.commerce.user.command.domain.QUser;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class StoreDaoImpl {

    private final StoreDao storeDao;
    private final JPAQueryFactory jpaQueryFactory;

    public Optional<StoreId> findStoreIdByUserEmail(String email) {
        QUser user = QUser.user;
        QStore store = QStore.store;
        return Optional.ofNullable(jpaQueryFactory
                .select(store.storeId)
                .from(store)
                .where(store.storeUserId.userId.eq(
                        JPAExpressions.select(user.id)
                                .where(user.email.eq(email))
                )).fetchOne());
    }

    public boolean existsByStoreUserId(StoreUserId storeUserId) {
        return storeDao.existsByStoreUserId(storeUserId);
    }

    public Optional<Store> findByStoreUserId(StoreUserId storeUserId) {
        return storeDao.findByStoreUserId(storeUserId);
    }
}
