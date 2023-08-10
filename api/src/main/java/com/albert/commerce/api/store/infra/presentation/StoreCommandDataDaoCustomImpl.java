package com.albert.commerce.api.store.infra.presentation;

import com.albert.commerce.api.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.api.store.command.application.dto.UpdateStoreRequest;
import com.albert.commerce.api.store.command.domain.QStore;
import com.albert.commerce.api.store.command.domain.Store;
import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.store.command.domain.StoreRepository;
import com.albert.commerce.api.store.infra.presentation.imports.StoreJpaRepository;
import com.albert.commerce.api.user.command.domain.UserId;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StoreCommandDataDaoCustomImpl implements StoreRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final StoreJpaRepository storeJpaRepository;
    private final SequenceGenerator sequenceGenerator;

    private StoreId nextId() {
        return StoreId.from(sequenceGenerator.generate());
    }

    @Override
    public Optional<Store> updateMyStore(UpdateStoreRequest updateStoreRequest,
            UserId userId) {
        QStore store = QStore.store;
        jpaQueryFactory
                .update(store)
                .set(store.storeName, updateStoreRequest.storeName())
                .set(store.address, updateStoreRequest.address())
                .set(store.email, updateStoreRequest.email())
                .set(store.ownerName, updateStoreRequest.ownerName())
                .set(store.phoneNumber, updateStoreRequest.phoneNumber())
                .where(store.userId.eq(userId))
                .execute();

        return Optional.ofNullable(jpaQueryFactory
                .selectFrom(store)
                .where(store.userId.eq(userId))
                .fetchFirst());
    }

    @Override
    public boolean existsByUserId(UserId userId) {
        return storeJpaRepository.existsByUserId(userId);
    }

    @Override
    public Store save(Store store) {
        store.updateId(nextId());
        return storeJpaRepository.save(store);
    }

    public Optional<Store> findByUserId(UserId userId) {
        return storeJpaRepository.findByUserId(userId);
    }
}
