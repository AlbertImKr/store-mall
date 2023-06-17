package com.albert.commerce.store.infra;

import com.albert.commerce.store.command.application.UpdateStoreRequest;
import com.albert.commerce.store.command.domain.QStore;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreUserId;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StoreCommandDaoCustomImpl implements StoreCommandDaoCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public Optional<Store> updateMyStore(UpdateStoreRequest updateStoreRequest,
            StoreUserId storeUserId) {
        QStore store = QStore.store;
        jpaQueryFactory
                .update(store)
                .set(store.storeName, updateStoreRequest.storeName())
                .set(store.address, updateStoreRequest.address())
                .set(store.email, updateStoreRequest.email())
                .set(store.ownerName, updateStoreRequest.ownerName())
                .set(store.phoneNumber, updateStoreRequest.phoneNumber())
                .where(store.storeUserId.eq(storeUserId))
                .execute();

        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(store)
                .where(store.storeUserId.eq(storeUserId))
                .fetchFirst());
    }
}
