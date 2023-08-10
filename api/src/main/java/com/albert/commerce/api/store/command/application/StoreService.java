package com.albert.commerce.api.store.command.application;

import com.albert.commerce.api.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.api.store.command.application.dto.UpdateStoreRequest;
import com.albert.commerce.api.store.command.domain.Store;
import com.albert.commerce.api.store.command.domain.StoreRepository;
import com.albert.commerce.api.user.command.application.UserService;
import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.exception.StoreAlreadyExistsException;
import com.albert.commerce.common.exception.StoreNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    @Transactional
    public DomainId createStore(String userEmail, NewStoreRequest newStoreRequest) {
        DomainId userId = userService.findIdByEmail(userEmail);
        if (storeRepository.existsByUserId(userId)) {
            throw new StoreAlreadyExistsException();
        }
        Store store = Store.builder()
                .userId(userId)
                .storeName(newStoreRequest.storeName())
                .ownerName(newStoreRequest.ownerName())
                .address(newStoreRequest.address())
                .phoneNumber(newStoreRequest.phoneNumber())
                .email(newStoreRequest.email())
                .build();
        return storeRepository.save(store).getStoreId();
    }

    @Transactional
    public void updateMyStore(UpdateStoreRequest updateStoreRequest, String userEmail) {
        DomainId userId = userService.findIdByEmail(userEmail);
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(StoreNotFoundException::new);
        store.update(updateStoreRequest.storeName(),
                updateStoreRequest.ownerName(),
                updateStoreRequest.address(),
                updateStoreRequest.email(),
                updateStoreRequest.phoneNumber());
    }

    @Transactional(readOnly = true)
    public Store getStoreByUserEmail(DomainId userId) {
        return storeRepository.findByUserId(userId).orElseThrow(StoreNotFoundException::new);
    }

    public boolean exists(DomainId storeId) {
        return storeRepository.exists(storeId);
    }
}
