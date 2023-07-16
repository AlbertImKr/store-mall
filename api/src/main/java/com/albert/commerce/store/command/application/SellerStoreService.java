package com.albert.commerce.store.command.application;

import com.albert.commerce.store.StoreNotFoundException;
import com.albert.commerce.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.store.command.application.dto.SellerStoreResponse;
import com.albert.commerce.store.command.application.dto.UpdateStoreRequest;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreRepository;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.domain.UserId;
import com.albert.commerce.user.query.domain.UserDao;
import com.albert.commerce.user.query.domain.UserData;
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
        return storeRepository.save(newStoreRequest.toStore(userId)).getStoreId();
    }

    @Transactional
    public SellerStoreResponse updateMyStore(UpdateStoreRequest updateStoreRequest,
            String userEmail) {
        UserData user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        return SellerStoreResponse.from(storeRepository.updateMyStore(updateStoreRequest, user.getUserId())
                .orElseThrow(StoreNotFoundException::new));
    }
}
