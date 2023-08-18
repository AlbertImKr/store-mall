package com.albert.commerce.query.infra.persistance;

import com.albert.commerce.query.domain.store.StoreData;
import com.albert.commerce.query.domain.store.StoreDataDao;
import com.albert.commerce.query.domain.store.StoreId;
import com.albert.commerce.query.domain.user.UserId;
import com.albert.commerce.query.infra.persistance.imports.StoreDataJpaRepository;
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
    public StoreData save(StoreData storeData) {
        return storeDataJpaRepository.save(storeData);
    }

    @Override
    public Optional<StoreData> findByUserId(UserId userId) {
        return storeDataJpaRepository.findByUserId(userId);
    }
}
