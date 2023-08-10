package com.albert.commerce.api.store.command.domain;

import com.albert.commerce.api.store.command.application.dto.UpdateStoreRequest;
import com.albert.commerce.api.user.command.domain.UserId;
import java.util.Optional;

public interface StoreRepository {

    boolean existsByUserId(UserId userId);

    Store save(Store store);

    Optional<Store> updateMyStore(UpdateStoreRequest updateStoreRequest, UserId userId);

    Optional<Store> findByUserId(UserId userId);
}
