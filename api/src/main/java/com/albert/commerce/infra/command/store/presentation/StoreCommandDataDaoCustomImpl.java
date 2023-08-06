package com.albert.commerce.infra.command.store.presentation;

import com.albert.commerce.application.command.store.dto.UpdateStoreRequest;
import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.domain.command.store.QStore;
import com.albert.commerce.domain.command.store.Store;
import com.albert.commerce.domain.command.store.StoreCreatedEvent;
import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.command.store.StoreRepository;
import com.albert.commerce.domain.command.user.UserId;
import com.albert.commerce.infra.command.store.presentation.imports.StoreJpaRepository;
import com.albert.commerce.messagingcore.domain.event.Events;
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
        Store save = storeJpaRepository.save(store);
        Events.raise(StoreCreatedEvent.from(store));
        return save;
    }
}
