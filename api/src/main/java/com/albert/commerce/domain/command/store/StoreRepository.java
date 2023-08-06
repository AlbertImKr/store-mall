package com.albert.commerce.domain.command.store;

import com.albert.commerce.application.command.store.dto.UpdateStoreRequest;
import com.albert.commerce.domain.command.user.UserId;
import java.util.Optional;

public interface StoreRepository {

    boolean existsByUserId(UserId userId);
    Store save(Store store);

    Optional<Store> updateMyStore(UpdateStoreRequest updateStoreRequest, UserId userId);

}
