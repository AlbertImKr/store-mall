package com.albert.commerce.api.store.command.application;

import com.albert.commerce.api.store.StoreNotFoundException;
import com.albert.commerce.api.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.api.store.command.application.dto.UpdateStoreRequest;
import com.albert.commerce.api.store.command.domain.Store;
import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.store.command.domain.StoreRepository;
import com.albert.commerce.api.user.command.application.UserService;
import com.albert.commerce.api.user.command.domain.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SellerStoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    @Transactional
    public StoreId createStore(String userEmail, NewStoreRequest newStoreRequest) {
        UserId userId = userService.findIdByEmail(userEmail);
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
        UserId userId = userService.findIdByEmail(userEmail);
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(StoreNotFoundException::new);
        store.update(updateStoreRequest.storeName(),
                updateStoreRequest.ownerName(),
                updateStoreRequest.address(),
                updateStoreRequest.email(),
                updateStoreRequest.phoneNumber());
    }

    @Transactional(readOnly = true)
    public Store getStoreByUserEmail(UserId userId) {
        return storeRepository.findByUserId(userId).orElseThrow(StoreNotFoundException::new);
    }
}
