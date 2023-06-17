package com.albert.commerce.store.command.application;

import com.albert.commerce.common.model.SequenceGenerator;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreRepository;
import com.albert.commerce.store.command.domain.StoreUserId;
import com.albert.commerce.store.ui.StoreNotFoundException;
import com.albert.commerce.user.command.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SellerStoreService {

    private final StoreRepository storeRepository;
    private final SequenceGenerator sequenceGenerator;

    public SellerStoreResponse createStore(NewStoreRequest newStoreRequest, UserId userId) {
        if (storeRepository.existsByStoreUserId(StoreUserId.from(userId))) {
            throw new StoreAlreadyExistsException();
        }
        Store store = newStoreRequest.toStore(
                StoreUserId.from(userId),
                StoreId.from(sequenceGenerator.generate()));
        Store savedStore = storeRepository.save(store);
        return SellerStoreResponse.from(savedStore);
    }

    public SellerStoreResponse updateMyStore(UpdateStoreRequest updateStoreRequest,
            StoreUserId storeUserId) {
        return SellerStoreResponse.from(
                storeRepository.updateMyStore(updateStoreRequest, storeUserId)
                        .orElseThrow(StoreNotFoundException::new));
    }
}
