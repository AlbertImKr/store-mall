package com.albert.commerce.api.store.query.domain;

import com.albert.commerce.api.common.domain.DomainId;
import com.albert.commerce.api.user.command.domain.UserId;
import java.util.Optional;

public interface StoreDataDao {

    Optional<StoreData> findById(DomainId storeId);

    boolean exists(DomainId storeId);

    StoreData save(StoreData storeData);

    Optional<StoreData> findByUserId(UserId userId);
}
