package com.albert.commerce.application.service;

import com.albert.commerce.application.port.out.StoreRepository;
import com.albert.commerce.application.service.command.StoreRegisterCommand;
import com.albert.commerce.application.service.command.StoreUploadCommand;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import com.albert.commerce.exception.error.StoreAlreadyExistsException;
import com.albert.commerce.exception.error.StoreNotFoundException;
import java.time.LocalDateTime;
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
    @ServiceActivator(inputChannel = "StoreRegisterCommand")
    public String create(StoreRegisterCommand storeRegisterCommand) {
        var userId = userService.getUserIdByEmail(storeRegisterCommand.getUserEmail());
        checkExistsByUserId(userId);
        var store = Store.from(getNewStoreId(), storeRegisterCommand, userId);
        return storeRepository.save(store)
                .getStoreId()
                .getValue();
    }

    @Transactional
    @ServiceActivator(inputChannel = "StoreUploadCommand")
    public boolean upload(StoreUploadCommand storeUploadCommand) {
        var userId = userService.getUserIdByEmail(storeUploadCommand.getUserEmail());
        var store = storeRepository.findByUserId(userId)
                .orElseThrow(StoreNotFoundException::new);
        uploadStore(storeUploadCommand, store);
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

    private StoreId getNewStoreId() {
        return storeRepository.nextId();
    }

    private void checkExistsByUserId(UserId userId) {
        if (storeRepository.existsByUserId(userId)) {
            throw new StoreAlreadyExistsException();
        }
    }

    private static void uploadStore(StoreUploadCommand storeUploadCommand, Store store) {
        LocalDateTime updatedTime = LocalDateTime.now();
        store.upload(
                storeUploadCommand.getStoreName(),
                storeUploadCommand.getOwnerName(),
                storeUploadCommand.getAddress(),
                storeUploadCommand.getEmail(),
                storeUploadCommand.getPhoneNumber(),
                updatedTime
        );
    }
}
