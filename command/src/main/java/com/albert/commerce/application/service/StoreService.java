package com.albert.commerce.application.service;

import com.albert.commerce.application.port.out.StoreRepository;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import com.albert.commerce.exception.StoreAlreadyExistsException;
import com.albert.commerce.exception.StoreNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserService userService;

    @Transactional
    @ServiceActivator(inputChannel = "StoreCreateCommand")
    public String create(StoreCreateCommand storeCreateCommand) {
        UserId userId = userService.getUserIdByEmail(storeCreateCommand.getUserEmail());
        if (storeRepository.existsByUserId(userId)) {
            throw new StoreAlreadyExistsException();
        }
        Store store = Store.builder()
                .userId(userId)
                .storeName(storeCreateCommand.getStoreName())
                .ownerName(storeCreateCommand.getOwnerName())
                .address(storeCreateCommand.getAddress())
                .phoneNumber(storeCreateCommand.getPhoneNumber())
                .email(storeCreateCommand.getEmail())
                .build();
        return storeRepository.save(store)
                .getStoreId()
                .getValue();
    }

    @Transactional
    @ServiceActivator(inputChannel = "StoreUpdateCommand")
    public boolean update(StoreUpdateCommand storeUpdateCommand) {
        UserId userId = userService.getUserIdByEmail(storeUpdateCommand.getUserEmail());
        Store store = storeRepository.findByUserId(userId)
                .orElseThrow(StoreNotFoundException::new);
        store.update(
                storeUpdateCommand.getStoreName(),
                storeUpdateCommand.getOwnerName(),
                storeUpdateCommand.getAddress(),
                storeUpdateCommand.getEmail(),
                storeUpdateCommand.getPhoneNumber()
        );
        return true;
    }

    @Transactional(readOnly = true)
    public Store getStoreByUserId(UserId userId) {
        return storeRepository.findByUserId(userId)
                .orElseThrow(StoreNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public StoreId getStoreIdByUserId(UserId userId) {
        return storeRepository.findByUserId(userId)
                .orElseThrow(StoreNotFoundException::new)
                .getStoreId();
    }

    @Transactional(readOnly = true)
    public void checkId(StoreId storeId) {
        if (storeRepository.existsById(storeId)) {
            return;
        }
        throw new StoreNotFoundException();
    }
}
