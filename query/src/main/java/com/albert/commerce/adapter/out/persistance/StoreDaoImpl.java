package com.albert.commerce.adapter.out.persistance;

import com.albert.commerce.adapter.out.persistance.imports.StoreJpaRepository;
import com.albert.commerce.application.port.out.StoreDao;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreId;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class StoreDaoImpl implements StoreDao {

    private final StoreJpaRepository storeJpaRepository;

    @Override
    public Optional<Store> findById(StoreId storeId) {
        return storeJpaRepository.findById(storeId);
    }
}
