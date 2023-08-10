package com.albert.commerce.api.store.command.application;

import com.albert.commerce.api.store.StoreNotFoundException;
import com.albert.commerce.api.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.api.store.command.application.dto.UpdateStoreRequest;
import com.albert.commerce.api.store.command.domain.Store;
import com.albert.commerce.api.store.command.domain.StoreId;
import com.albert.commerce.api.store.command.domain.StoreRepository;
import com.albert.commerce.api.user.UserNotFoundException;
import com.albert.commerce.api.user.command.domain.UserId;
import com.albert.commerce.api.user.query.domain.UserDao;
import com.albert.commerce.api.user.query.domain.UserData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SellerStoreService {

    private final StoreRepository storeRepository;
    private final UserDao userDao;

    @Transactional
    public StoreId createStore(String userEmail, NewStoreRequest newStoreRequest) {
        UserData userData = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        UserId userId = userData.getUserId();
        if (storeRepository.existsByUserId(userId)) {
            throw new StoreAlreadyExistsException();
        }
        Store store = Store.builder()
                .userId(userId)
                .storeName(newStoreRequest.storeName())
                .ownerName(newStoreRequest.ownerName())
                .address(newStoreRequest.address())
                .phoneNumber(newStoreRequest.phoneNumber())
                .email(newStoreRequest.email())
                .build();
        return storeRepository.save(store).getStoreId();
    }

    @Transactional
    public StoreId updateMyStore(UpdateStoreRequest updateStoreRequest, String userEmail) {
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        return storeRepository.updateMyStore(updateStoreRequest, user.getUserId())
                .orElseThrow(StoreNotFoundException::new).getStoreId();
    }

    public Store getStoreByUserEmail(UserId userId) {
        return storeRepository.findByUserId(userId).orElseThrow(StoreNotFoundException::new);
    }
}
