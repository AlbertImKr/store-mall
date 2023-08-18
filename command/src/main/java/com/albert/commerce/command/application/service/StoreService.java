package com.albert.commerce.command.application.service;

import com.albert.commerce.command.application.port.out.StoreRepository;
import com.albert.commerce.command.domain.store.Store;
import com.albert.commerce.command.domain.store.StoreId;
import com.albert.commerce.command.domain.user.UserId;
import com.albert.commerce.common.exception.StoreAlreadyExistsException;
import com.albert.commerce.common.exception.StoreNotFoundException;
import com.albert.commerce.shared.messaging.application.StoreCreateCommand;
import com.albert.commerce.shared.messaging.application.StoreUpdateCommand;
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
    public void update(StoreUpdateCommand storeUpdateCommand) {
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
    }

    @Transactional(readOnly = true)
    public Store getByUserId(UserId userId) {
        return storeRepository.findByUserId(userId)
                .orElseThrow(StoreNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public void checkId(StoreId storeId) {
        if (storeRepository.existsById(storeId)) {
            return;
        }
        throw new StoreNotFoundException();
    }
}
