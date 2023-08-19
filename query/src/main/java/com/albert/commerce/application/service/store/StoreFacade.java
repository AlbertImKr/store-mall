package com.albert.commerce.application.service.store;

import com.albert.commerce.application.service.store.dto.StoreUpdatedEvent;
import com.albert.commerce.domain.store.Store;
import com.albert.commerce.domain.store.StoreDao;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.User;
import com.albert.commerce.domain.user.UserDao;
import com.albert.commerce.exception.StoreNotFoundException;
import com.albert.commerce.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class StoreFacade {

    private final UserDao userDao;
    private final StoreDao storeDao;

    @Transactional(readOnly = true)
    public Store getMyStoreByUserEmail(String userEmail) {
        User user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        return storeDao.findByUserId(user.getUserId()).orElseThrow(StoreNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Store getStoreById(StoreId storeId) {
        return storeDao.findById(storeId).orElseThrow(StoreNotFoundException::new);
    }

    public void save(Store store) {
        storeDao.save(store);
    }

    @Transactional
    public void update(StoreUpdatedEvent storeUpdatedEvent) {
        Store store = storeDao.findById(storeUpdatedEvent.storeId())
                .orElseThrow(StoreNotFoundException::new);
        store.update(
                storeUpdatedEvent.storeName(),
                storeUpdatedEvent.ownerName(),
                storeUpdatedEvent.address(),
                storeUpdatedEvent.phoneNumber(),
                storeUpdatedEvent.email()
        );
    }
}
