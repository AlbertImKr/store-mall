package com.albert.commerce.command.domain.store;

import com.albert.commerce.command.domain.user.UserId;
import java.util.Optional;

public interface StoreRepository {

    boolean existsByUserId(UserId userId);

    Store save(Store store);

    Optional<Store> findByUserId(UserId userId);

    boolean existsById(StoreId storeId);
}
