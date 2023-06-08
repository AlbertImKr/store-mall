package com.albert.commerce.store.command.application;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreRepository;
import com.albert.commerce.store.query.StoreDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreDao storeDao;

    public StoreId addStore(StoreRequest storeRequest) {
        if (storeDao.existsByStoreUserId(storeRequest.getStoreUserId())) {
            throw new StoreAlreadyExistsError();
        }
        Store store = storeRequest.toStore();
        Store save = storeRepository.save(store);
        return save.getStoreId();
    }

}
