package com.albert.commerce.adapter.out.persistence;

import com.albert.commerce.adapter.out.persistence.imports.StoreJpaRepository;
import com.albert.commerce.application.port.out.StoreRepository;
import com.albert.commerce.application.port.out.persistence.SequenceGenerator;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
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
        return storeJpaRepository.save(store);
    }

    @Override
    public boolean existsById(StoreId storeId) {
        return storeJpaRepository.existsById(storeId);
    }

    @Override
    public StoreId nextId() {
        return StoreId.from(sequenceGenerator.generate());
    }
}
