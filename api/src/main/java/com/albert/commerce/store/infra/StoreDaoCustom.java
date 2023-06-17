package com.albert.commerce.store.infra;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;

public interface StoreDaoCustom {

    StoreId findStoreIdByUserEmail(String email);

    Store findStoreByUserEmail(String email);
}
