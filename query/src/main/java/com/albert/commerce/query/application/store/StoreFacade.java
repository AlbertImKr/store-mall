package com.albert.commerce.query.application.store;

import com.albert.commerce.query.application.store.dto.StoreUpdatedEvent;
import com.albert.commerce.query.common.exception.StoreNotFoundException;
import com.albert.commerce.query.common.exception.UserNotFoundException;
import com.albert.commerce.query.domain.store.StoreData;
import com.albert.commerce.query.domain.store.StoreDataDao;
import com.albert.commerce.query.domain.store.StoreId;
import com.albert.commerce.query.domain.user.UserDao;
import com.albert.commerce.query.domain.user.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class StoreFacade {

    private final UserDao userDao;
    private final StoreDataDao storeDataDao;

    @Transactional(readOnly = true)
    public StoreData getMyStoreByUserEmail(String userEmail) {
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        return storeDataDao.findByUserId(user.getUserId()).orElseThrow(StoreNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public StoreData getStoreById(StoreId storeId) {
        return storeDataDao.findById(storeId).orElseThrow(StoreNotFoundException::new);
    }

    public void save(StoreData storeData) {
        storeDataDao.save(storeData);
    }

    @Transactional
    public void update(StoreUpdatedEvent storeUpdatedEvent) {
        StoreData storeData = storeDataDao.findById(storeUpdatedEvent.storeId())
                .orElseThrow(StoreNotFoundException::new);
        storeData.update(
                storeUpdatedEvent.storeName(),
                storeUpdatedEvent.ownerName(),
                storeUpdatedEvent.address(),
                storeUpdatedEvent.phoneNumber(),
                storeUpdatedEvent.email()
        );
    }
}
