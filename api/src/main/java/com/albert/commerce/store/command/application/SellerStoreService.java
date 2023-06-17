package com.albert.commerce.store.command.application;

import com.albert.commerce.common.model.SequenceGenerator;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SellerStoreService {

    private final StoreRepository storeRepository;
    private final SequenceGenerator sequenceGenerator;

    public SellerStoreResponse createStore(NewStoreRequest newStoreRequest) {
        if (storeRepository.existsByStoreUserId(newStoreRequest.getStoreUserId())) {
            throw new StoreAlreadyExistsException();
        }
        Store store = newStoreRequest.toStore(new StoreId(sequenceGenerator.generate()));
        Store savedStore = storeRepository.save(store);
        return SellerStoreResponse.from(savedStore);
    }

}
