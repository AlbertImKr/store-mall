package com.albert.commerce.store.query.application;

import com.albert.commerce.store.StoreNotFoundException;
import com.albert.commerce.store.command.application.dto.ConsumerStoreResponse;
import com.albert.commerce.store.command.application.dto.SellerStoreResponse;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.query.domain.StoreDao;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.domain.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class StoreFacade {

    private final StoreDao storeDao;
    private final UserDao userDao;

    @Transactional(readOnly = true)
    public SellerStoreResponse findStoreByUserEmail(String userEmail) {
        User user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Store store = storeDao.findStoreByUserId(user.getId())
                .orElseThrow(StoreNotFoundException::new);
        return SellerStoreResponse.from(store);
    }

    public ConsumerStoreResponse findById(StoreId storeId) {
        Store store = storeDao.findById(storeId).orElseThrow(StoreNotFoundException::new);
        return ConsumerStoreResponse.from(store);
    }

    public void checkId(StoreId storeId) {
        if (!storeDao.exists(storeId)) {
            throw new StoreNotFoundException();
        }
    }
}
