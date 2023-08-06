package com.albert.commerce.application.query.store;

import com.albert.commerce.common.exception.StoreNotFoundException;
import com.albert.commerce.common.exception.UserNotFoundException;
import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.query.store.StoreData;
import com.albert.commerce.domain.query.store.StoreDataDao;
import com.albert.commerce.domain.query.user.UserDao;
import com.albert.commerce.domain.query.user.UserData;
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
