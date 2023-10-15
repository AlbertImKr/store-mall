package com.albert.commerce.application.port.out;

import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;

public interface StoreRepository {

    boolean existsByUserId(UserId userId);

    Store save(Store store);

    Optional<Store> findByUserId(UserId userId);

    boolean existsById(StoreId storeId);

    StoreId nextId();

    void deleteByUserId(UserId userId);
}
