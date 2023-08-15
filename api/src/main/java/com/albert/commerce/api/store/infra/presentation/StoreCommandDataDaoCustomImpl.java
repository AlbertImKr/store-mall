package com.albert.commerce.api.store.infra.presentation;

import com.albert.commerce.api.store.command.domain.Store;
import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.store.command.domain.StoreRepository;
import com.albert.commerce.api.store.infra.presentation.imports.StoreJpaRepository;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StoreCommandDataDaoCustomImpl implements StoreRepository {

    private final StoreJpaRepository storeJpaRepository;
    private final SequenceGenerator sequenceGenerator;

    @Override
    public boolean existsByUserId(UserId userId) {
        return storeJpaRepository.existsByUserId(userId);
    }

    @Override
    public Optional<Store> findByUserId(UserId userId) {
        return storeJpaRepository.findByUserId(userId);
    }

    @Override
    public Store save(Store store) {
        store.updateId(nextId());
        return storeJpaRepository.save(store);
    }

    @Override
    public boolean existsById(StoreId storeId) {
        return storeJpaRepository.existsById(storeId);
    }

    private StoreId nextId() {
        return StoreId.from(sequenceGenerator.generate());
    }
}
