package com.albert.commerce.store.infra.presentation;

import com.albert.commerce.store.command.domain.QStore;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.infra.presentation.imports.StoreJpaRepository;
import com.albert.commerce.store.query.StoreDao;
import com.albert.commerce.store.ui.StoreNotFoundException;
import com.albert.commerce.user.command.domain.QUser;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StoreDaoImpl implements StoreDao {

    private final JPAQueryFactory jpaQueryFactory;
    private final StoreJpaRepository storeJpaRepository;

    @Override
    public Store findStoreByUserEmail(String email) {
        QUser user = QUser.user;
        QStore store = QStore.store;
        Store targetStore = jpaQueryFactory
                .select(store)
                .from(store)
                .where(store.storeUserId.userId.eq(
                        JPAExpressions
                                .select(user.id)
                                .from(user)
                                .where(user.email.eq(email))
                )).fetchOne();
        if (targetStore == null) {
            throw new StoreNotFoundException();
        }
        return targetStore;
    }

    @Override
    public StoreId findStoreIdByUserEmail(String email) {
        QUser user = QUser.user;
        QStore store = QStore.store;
        StoreId storeId = jpaQueryFactory
                .select(store.storeId)
                .from(store)
                .where(store.storeUserId.userId.eq(
                        JPAExpressions
                                .select(user.id)
                                .from(user)
                                .where(user.email.eq(email))
                )).fetchOne();

        if (storeId == null) {
            throw new StoreNotFoundException();
        }
        return storeId;
    }

    @Override
    public Optional<Store> findById(StoreId storeId) {
        return storeJpaRepository.findById(storeId);
    }
}
