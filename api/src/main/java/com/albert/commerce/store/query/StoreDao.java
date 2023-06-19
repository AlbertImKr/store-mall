package com.albert.commerce.store.query;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import java.util.Optional;

public interface StoreDao {

    Optional<Store> findById(StoreId storeId);

    Store findStoreByUserEmail(String email);

    StoreId findStoreIdByUserEmail(String email);
}
