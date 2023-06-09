package com.albert.commerce.store.command.application;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreRepository;
import com.albert.commerce.store.query.StoreDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final StoreDao storeDao;

    public StoreResponse addStore(StoreRequest storeRequest) {
        if (storeDao.existsByStoreUserId(storeRequest.getStoreUserId())) {
            throw new StoreAlreadyExistsError();
        }
        Store store = storeRequest.toStore();
        Store savedStore = storeRepository.save(store);
        return StoreResponse.from(savedStore);
    }

}
