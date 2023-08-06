package com.albert.commerce.application.command.store;

import com.albert.commerce.application.command.store.dto.NewStoreRequest;
import com.albert.commerce.application.command.store.dto.UpdateStoreRequest;
import com.albert.commerce.common.exception.StoreNotFoundException;
import com.albert.commerce.common.exception.UserNotFoundException;
import com.albert.commerce.domain.command.store.Store;
import com.albert.commerce.domain.command.store.StoreId;
import com.albert.commerce.domain.command.store.StoreRepository;
import com.albert.commerce.domain.command.user.UserId;
import com.albert.commerce.domain.query.user.UserDao;
import com.albert.commerce.domain.query.user.UserData;
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
        Store save = storeRepository.save(store);
        return save.getStoreId();
    }

    @Transactional
    public StoreId updateMyStore(UpdateStoreRequest updateStoreRequest, String userEmail) {
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        return storeRepository.updateMyStore(updateStoreRequest, user.getUserId())
                .orElseThrow(StoreNotFoundException::new).getStoreId();
    }
}
