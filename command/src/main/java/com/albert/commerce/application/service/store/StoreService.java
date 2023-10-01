package com.albert.commerce.application.service.store;

import com.albert.commerce.application.port.out.StoreRepository;
import com.albert.commerce.application.service.exception.error.StoreAlreadyExistsException;
import com.albert.commerce.application.service.exception.error.StoreNotFoundException;
import com.albert.commerce.application.service.user.UserService;
import com.albert.commerce.application.service.utils.Success;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
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
        validateAlreadyExists(userId);
        var store = Store.from(getNewStoreId(), storeRegisterCommand, userId);
        return storeRepository.save(store)
                .getStoreId()
                .getValue();
    }

    @Transactional
    @ServiceActivator(inputChannel = "StoreUploadCommand")
    public Success upload(StoreUploadCommand storeUploadCommand) {
        var userId = userService.getUserIdByEmail(storeUploadCommand.getUserEmail());
        var store = getStoreByUserId(userId);
        uploadStore(storeUploadCommand, store);
        return Success.getInstance();
    }

    @Transactional
    @ServiceActivator(inputChannel = "StoreDeleteCommand")
    public Success delete(StoreDeleteCommand storeDeleteCommand) {
        var userId = userService.getUserIdByEmail(storeDeleteCommand.getUserEmail());
        validateExistsByUserId(userId);
        storeRepository.deleteByUserId(userId);
        return Success.getInstance();
    }

    private void validateExistsByUserId(UserId userId) {
        if (!storeRepository.existsByUserId(userId)) {
            throw new StoreNotFoundException();
        }
    }

    public Store getStoreByUserId(UserId userId) {
        return storeRepository.findByUserId(userId)
                .orElseThrow(StoreNotFoundException::new);
    }

    public StoreId getStoreIdByUserId(UserId userId) {
        return storeRepository.findByUserId(userId)
                .orElseThrow(StoreNotFoundException::new)
                .getStoreId();
    }

    public void checkId(StoreId storeId) {
        if (storeRepository.existsById(storeId)) {
            return;
        }
        throw new StoreNotFoundException();
    }

    private StoreId getNewStoreId() {
        return storeRepository.nextId();
    }

    private void validateAlreadyExists(UserId userId) {
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
