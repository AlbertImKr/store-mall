package com.albert.commerce.api.store.query.application;

import com.albert.commerce.api.store.query.application.dto.UpdateStoreRequest;
import com.albert.commerce.api.store.query.domain.StoreData;
import com.albert.commerce.api.store.query.domain.StoreDataDao;
import com.albert.commerce.api.user.query.domain.UserDao;
import com.albert.commerce.api.user.query.domain.UserData;
import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.common.exception.StoreNotFoundException;
import com.albert.commerce.common.exception.UserNotFoundException;
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
    public StoreData getStoreById(DomainId storeId) {
        return storeDataDao.findById(storeId).orElseThrow(StoreNotFoundException::new);
    }

    public void save(StoreData storeData) {
        storeDataDao.save(storeData);
    }

    @Transactional
    public void update(UpdateStoreRequest updateStoreRequest) {
        StoreData storeData = storeDataDao.findById(updateStoreRequest.storeId())
                .orElseThrow(StoreNotFoundException::new);
        storeData.update(
                updateStoreRequest.storeName(),
                updateStoreRequest.ownerName(),
                updateStoreRequest.address(),
                updateStoreRequest.phoneNumber(),
                updateStoreRequest.email()
        );
    }
}
