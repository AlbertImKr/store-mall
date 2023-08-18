package com.albert.commerce.command.application.service;

import com.albert.commerce.command.adapter.in.web.dto.NewStoreRequest;
import com.albert.commerce.command.adapter.in.web.dto.UpdateStoreRequest;
import com.albert.commerce.command.application.port.out.StoreRepository;
import com.albert.commerce.command.domain.store.Store;
import com.albert.commerce.command.domain.store.StoreId;
import com.albert.commerce.command.domain.user.UserId;
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
    public Store getStoreByUserId(UserId userId) {
        return storeRepository.findByUserId(userId).orElseThrow(StoreNotFoundException::new);
    }

    public void checkId(StoreId storeId) {
        if (storeRepository.existsById(storeId)) {
            return;
        }
        throw new StoreNotFoundException();
    }
}
