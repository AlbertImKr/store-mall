package com.albert.commerce.api.store.infra.presentation;

import com.albert.commerce.api.common.domain.DomainId;
import com.albert.commerce.api.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.api.store.command.domain.Store;
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

    private DomainId nextId() {
        return DomainId.from(sequenceGenerator.generate());
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

    @Override
    public Optional<Store> findByUserId(UserId userId) {
        return storeJpaRepository.findByUserId(userId);
    }

    @Override
    public boolean exists(DomainId storeId) {
        return storeJpaRepository.existsById(storeId);
    }
}
