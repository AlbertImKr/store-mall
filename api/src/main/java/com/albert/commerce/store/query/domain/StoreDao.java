package com.albert.commerce.store.query.domain;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.user.command.domain.UserId;
import java.util.Optional;

public interface StoreDao {

    Optional<Store> findById(StoreId storeId);

    boolean exists(StoreId storeId);

    Optional<Store> findStoreByUserId(UserId id);
}
