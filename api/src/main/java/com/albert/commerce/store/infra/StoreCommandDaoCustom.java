package com.albert.commerce.store.infra;

import com.albert.commerce.store.command.application.UpdateStoreRequest;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreUserId;
import java.util.Optional;

public interface StoreCommandDaoCustom {

    Optional<Store> updateMyStore(UpdateStoreRequest updateStoreRequest, StoreUserId storeUserId);
}
