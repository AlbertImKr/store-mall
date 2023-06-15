package com.albert.commerce.store.application;

import com.albert.commerce.common.SequenceGenerator;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreRepository;
import com.albert.commerce.store.query.StoreDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SellerStoreService {

    private final StoreRepository storeRepository;
    private final StoreDao storeDao;
    private final SequenceGenerator sequenceGenerator;

    public SellerStoreResponse createStore(NewStoreRequest newStoreRequest) {
        if (storeDao.existsByStoreUserId(newStoreRequest.getStoreUserId())) {
            throw new StoreAlreadyExistsException();
        }
        Store store = newStoreRequest.toStore(new StoreId(sequenceGenerator.generate()));
        Store savedStore = storeRepository.save(store);
        return SellerStoreResponse.from(savedStore);
    }

}
