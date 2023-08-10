package com.albert.commerce.api.store.infra.presentation;

import com.albert.commerce.api.store.infra.presentation.imports.StoreDataJpaRepository;
import com.albert.commerce.api.store.query.domain.StoreData;
import com.albert.commerce.api.store.query.domain.StoreDataDao;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.common.domain.DomainId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StoreDataDaoImpl implements StoreDataDao {

    private final StoreDataJpaRepository storeDataJpaRepository;

    @Override
    public Optional<StoreData> findById(DomainId storeId) {
        return storeDataJpaRepository.findById(storeId);
    }

    @Override
    public boolean exists(DomainId storeId) {
        return storeDataJpaRepository.existsById(storeId);
    }

    @Override
    public StoreData save(StoreData storeData) {
        return storeDataJpaRepository.save(storeData);
    }

    @Override
    public Optional<StoreData> findByUserId(UserId userId) {
        return storeDataJpaRepository.findByUserId(userId);
    }
}
