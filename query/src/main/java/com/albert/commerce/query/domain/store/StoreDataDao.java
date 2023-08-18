package com.albert.commerce.query.domain.store;

import com.albert.commerce.query.domain.user.UserId;
import java.util.Optional;

public interface StoreDataDao {

    Optional<StoreData> findById(StoreId storeId);

    boolean exists(StoreId storeId);

    StoreData save(StoreData storeData);

    Optional<StoreData> findByUserId(UserId userId);
}
