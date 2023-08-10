package com.albert.commerce.api.store.command.domain;

import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.common.domain.DomainId;
import java.util.Optional;

public interface StoreRepository {

    boolean existsByUserId(UserId userId);

    Store save(Store store);

    Optional<Store> findByUserId(UserId userId);

    boolean exists(DomainId storeId);
}
