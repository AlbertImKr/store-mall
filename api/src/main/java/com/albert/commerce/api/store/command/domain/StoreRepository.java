package com.albert.commerce.api.store.command.domain;

import com.albert.commerce.common.domain.DomainId;
import java.util.Optional;

public interface StoreRepository {

    boolean existsByUserId(DomainId userId);

    Store save(Store store);

    Optional<Store> findByUserId(DomainId userId);

    boolean existsById(DomainId storeId);
}
