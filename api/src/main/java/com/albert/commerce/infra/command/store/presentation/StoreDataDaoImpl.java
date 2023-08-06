package com.albert.commerce.infra.command.store.presentation;

import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.command.user.UserId;
import com.albert.commerce.domain.query.store.StoreData;
import com.albert.commerce.domain.query.store.StoreDataDao;
import com.albert.commerce.infra.command.store.presentation.imports.StoreDataJpaRepository;
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
