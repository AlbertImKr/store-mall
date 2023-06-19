package com.albert.commerce.store.command.domain;

import com.albert.commerce.store.command.application.UpdateStoreRequest;
import java.util.Optional;

public interface StoreRepository {

    boolean existsByStoreUserId(StoreUserId from);

    Store save(Store store);

    Optional<Store> updateMyStore(UpdateStoreRequest updateStoreRequest, String email);
}
