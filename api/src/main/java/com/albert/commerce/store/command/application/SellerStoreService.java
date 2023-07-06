package com.albert.commerce.store.command.application;

import com.albert.commerce.common.infra.persistence.SequenceGenerator;
import com.albert.commerce.store.StoreNotFoundException;
import com.albert.commerce.store.command.application.dto.NewStoreRequest;
import com.albert.commerce.store.command.application.dto.SellerStoreResponse;
import com.albert.commerce.store.command.application.dto.UpdateStoreRequest;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreRepository;
import com.albert.commerce.user.UserNotFoundException;
import com.albert.commerce.user.command.domain.User;
import com.albert.commerce.user.query.domain.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SellerStoreService {

    private final StoreRepository storeRepository;
    private final UserDao userDao;
    private final SequenceGenerator sequenceGenerator;

    public SellerStoreResponse createStore(NewStoreRequest newStoreRequest, String userEmail) {
        User user = userDao.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        if (storeRepository.existsByUserId(user.getId())) {
            throw new StoreAlreadyExistsException();
        }
        Store store = newStoreRequest.toStore(
                user.getId(),
                StoreId.from(sequenceGenerator.generate()));
        Store savedStore = storeRepository.save(store);
        return SellerStoreResponse.from(savedStore);
    }

    public SellerStoreResponse updateMyStore(UpdateStoreRequest updateStoreRequest,
            String email) {
        return SellerStoreResponse.from(
                storeRepository.updateMyStore(updateStoreRequest, email)
                        .orElseThrow(StoreNotFoundException::new));
    }
}
