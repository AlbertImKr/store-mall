package com.albert.commerce.store.command.domain;

import com.albert.commerce.store.command.application.dto.UpdateStoreRequest;
import com.albert.commerce.user.command.domain.UserId;
import java.util.Optional;

public interface StoreRepository {

    boolean existsByUserId(UserId userId);

    Store save(Store store);

    Optional<Store> updateMyStore(UpdateStoreRequest updateStoreRequest, UserId userId);


    StoreId nextId();
}
