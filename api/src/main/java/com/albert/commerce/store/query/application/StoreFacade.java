package com.albert.commerce.store.query.application;

import com.albert.commerce.store.StoreNotFoundException;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.query.domain.StoreData;
import com.albert.commerce.store.query.domain.StoreDataDao;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.query.domain.UserDao;
import com.albert.commerce.user.query.domain.UserData;
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

    public StoreData getStoreById(StoreId storeId) {
        return storeDataDao.findById(storeId).orElseThrow(StoreNotFoundException::new);
    }
}
