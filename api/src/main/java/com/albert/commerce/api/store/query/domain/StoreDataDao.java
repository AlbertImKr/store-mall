package com.albert.commerce.api.store.query.domain;

import com.albert.commerce.common.domain.DomainId;
import java.util.Optional;

public interface StoreDataDao {

    Optional<StoreData> findById(DomainId storeId);

    boolean exists(DomainId storeId);

    StoreData save(StoreData storeData);

    Optional<StoreData> findByUserId(DomainId userId);
}
