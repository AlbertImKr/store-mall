package com.albert.commerce.store.command.application;

import com.albert.commerce.store.StoreNotFoundException;
import com.albert.commerce.store.command.application.dto.SellerStoreResponse;
import com.albert.commerce.store.command.application.dto.UpdateStoreRequest;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreRepository;
import com.albert.commerce.user.command.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SellerStoreService {

    private final StoreRepository storeRepository;

    public SellerStoreResponse createStore(Store store) {
        if (storeRepository.existsByUserId(store.getUserId())) {
            throw new StoreAlreadyExistsException();
        }
        Store savedStore = storeRepository.save(store);
        return SellerStoreResponse.from(savedStore);
    }

    public SellerStoreResponse updateMyStore(UpdateStoreRequest updateStoreRequest,
            UserId userId) {
        return SellerStoreResponse.from(storeRepository.updateMyStore(updateStoreRequest, userId)
                .orElseThrow(StoreNotFoundException::new));
    }
}
