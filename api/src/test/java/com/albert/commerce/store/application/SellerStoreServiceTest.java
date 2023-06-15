package com.albert.commerce.store.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.albert.commerce.common.model.SequenceGenerator;
import com.albert.commerce.common.model.SequenceGeneratorImpl;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreRepository;
import com.albert.commerce.store.query.StoreDao;
import com.albert.commerce.user.command.domain.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class SellerStoreServiceTest {

    private SellerStoreService sellerStoreService;
    private StoreRepository storeRepository;
    private StoreDao storeDao;
    private SequenceGenerator sequenceGenerator;

    @BeforeEach
    void setUserService() {
        storeRepository = mock(StoreRepository.class);
        storeDao = mock(StoreDao.class);
        sequenceGenerator = new SequenceGeneratorImpl();
        sellerStoreService = new SellerStoreService(storeRepository, storeDao, sequenceGenerator);
    }

    @DisplayName("User는 Store를 하나 만들 수 있습니다.")
    @Test
    void addStoreSuccess() {
        // given
        NewStoreRequest newStoreRequest = new NewStoreRequest("test");
        newStoreRequest.setUserId(UserId.from(sequenceGenerator.generate()));
        Store store = newStoreRequest.toStore(new StoreId(sequenceGenerator.generate()));
        given(storeRepository.save(any())).willReturn(store);

        // when
        SellerStoreResponse sellerStoreResponse = sellerStoreService.createStore(newStoreRequest);

        // then
        assertThat(store.getStoreId()).isEqualTo(sellerStoreResponse.getStoreId());
    }

    @DisplayName("User는 이미 Store를 하나 만들었으면 에외를 던진다.")
    @Test
    void addStoreFailed() {
        // given
        NewStoreRequest newStoreRequest = new NewStoreRequest("test");
        newStoreRequest.setUserId(UserId.from(sequenceGenerator.generate()));
        given(storeDao.existsByStoreUserId(any())).willReturn(true);

        // when,then
        assertThatThrownBy(() -> sellerStoreService.createStore(newStoreRequest)).isInstanceOf(
                StoreAlreadyExistsException.class);
    }
}
