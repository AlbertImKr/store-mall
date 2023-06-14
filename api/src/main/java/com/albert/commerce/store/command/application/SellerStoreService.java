package com.albert.commerce.store.command.application;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SellerStoreService {

    private final StoreRepository storeRepository;

    public SellerStoreResponse addStore(NewStoreRequest newStoreRequest) {
        Store store = newStoreRequest.toStore();
        Store savedStore = storeRepository.save(store);
        return SellerStoreResponse.from(savedStore);
    }

}
