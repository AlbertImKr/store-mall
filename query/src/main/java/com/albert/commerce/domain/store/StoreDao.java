package com.albert.commerce.domain.store;

import com.albert.commerce.domain.user.UserId;
import java.util.Optional;

public interface StoreDao {

    Optional<Store> findById(StoreId storeId);

    boolean exists(StoreId storeId);

    Store save(Store store);

    Optional<Store> findByUserId(UserId userId);
}
