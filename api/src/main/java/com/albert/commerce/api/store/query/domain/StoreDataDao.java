package com.albert.commerce.api.store.query.domain;

import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.user.command.domain.UserId;
import java.util.Optional;

public interface StoreDataDao {

    Optional<StoreData> findById(StoreId storeId);

    boolean exists(StoreId storeId);

    Optional<StoreData> getMyStoreByUserEmail(UserId id);

    Optional<StoreData> findByUserId(UserId userId);
}
