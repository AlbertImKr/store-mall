package com.albert.commerce.store.query;

import com.albert.commerce.store.command.domain.StoreId;
import java.util.Optional;

public interface StoreDaoCustom {

    Optional<StoreId> findStoreIdByUserEmail(String email);
}