package com.albert.commerce.application.port.out;

import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreId;
import java.util.Optional;

public interface StoreDao {

    Optional<Store> findById(StoreId storeId);
}
