package com.albert.commerce.domain.query.store;

import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.command.user.UserId;
import java.util.Optional;

public interface StoreDataDao {

    Optional<StoreData> findById(StoreId storeId);

    boolean exists(StoreId storeId);

    Optional<StoreData> getMyStoreByUserEmail(UserId id);

    Optional<StoreData> findByUserId(UserId userId);
}
