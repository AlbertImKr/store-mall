package com.albert.commerce.adapter.out.persistance;

import com.albert.commerce.adapter.out.persistance.imports.StoreDataJpaRepository;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreDao;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StoreDaoImpl implements StoreDao {

    private final StoreDataJpaRepository storeDataJpaRepository;

    @Override
    public Optional<Store> findById(StoreId storeId) {
        return storeDataJpaRepository.findById(storeId);
    }

    @Override
    public boolean exists(StoreId storeId) {
        return storeDataJpaRepository.existsById(storeId);
    }

    @Override
    public Store save(Store store) {
        return storeDataJpaRepository.save(store);
    }

    @Override
    public Optional<Store> findByUserId(UserId userId) {
        return storeDataJpaRepository.findByUserId(userId);
    }
}
