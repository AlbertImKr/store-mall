package com.albert.commerce.store.infra.presentation;

import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.infra.presentation.imports.StoreDataJpaRepository;
import com.albert.commerce.store.query.domain.StoreData;
import com.albert.commerce.store.query.domain.StoreDataDao;
import com.albert.commerce.user.command.domain.UserId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StoreDataDaoImpl implements StoreDataDao {

    private final StoreDataJpaRepository storeDataJpaRepository;


    @Override
    public Optional<StoreData> findById(StoreId storeId) {
        return storeDataJpaRepository.findById(storeId);
    }

    @Override
    public boolean exists(StoreId storeId) {
        return storeDataJpaRepository.existsById(storeId);
    }

    @Override
    public Optional<StoreData> getMyStoreByUserEmail(UserId userId) {
        return storeDataJpaRepository.findByUserId(userId);
    }

    @Override
    public Optional<StoreData> findByUserId(UserId userId) {
        return storeDataJpaRepository.findByUserId(userId);
    }
}
