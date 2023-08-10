package com.albert.commerce.api.store.infra.presentation;

import com.albert.commerce.api.store.command.domain.Store;
import com.albert.commerce.api.store.command.domain.StoreRepository;
import com.albert.commerce.api.store.infra.presentation.imports.StoreJpaRepository;
import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StoreCommandDataDaoCustomImpl implements StoreRepository {

    private final StoreJpaRepository storeJpaRepository;
    private final SequenceGenerator sequenceGenerator;

    private DomainId nextId() {
        return DomainId.from(sequenceGenerator.generate());
    }

    @Override
    public boolean existsByUserId(DomainId userId) {
        return storeJpaRepository.existsByUserId(userId);
    }

    @Override
    public Store save(Store store) {
        store.updateId(nextId());
        return storeJpaRepository.save(store);
    }

    @Override
    public Optional<Store> findByUserId(DomainId userId) {
        return storeJpaRepository.findByUserId(userId);
    }

    @Override
    public boolean exists(DomainId storeId) {
        return storeJpaRepository.existsById(storeId);
    }
}
