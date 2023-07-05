package com.albert.commerce.store.query;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;

public interface StoreDao {

    Store findById(StoreId storeId);

    Store findStoreByUserEmail(String email);

    StoreId findStoreIdByUserEmail(String email);

    boolean exists(StoreId storeId);
}
