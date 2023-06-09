package com.albert.commerce.store.command.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreRepository;
import com.albert.commerce.store.query.StoreDao;
import com.albert.commerce.user.command.domain.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class StoreServiceTest {

    private StoreService storeService;
    private StoreRepository storeRepository;
    private StoreDao storeDao;

    @BeforeEach
    void setUserService() {
        storeRepository = mock(StoreRepository.class);
        storeDao = mock(StoreDao.class);

        storeService = new StoreService(storeRepository, storeDao);
    }

    @DisplayName("User는 Store를 하나 만들 수 있습니다.")
    @Test
    void addStoreSuccess() {
        // given
        StoreRequest storeRequest = new StoreRequest("test");
        storeRequest.setUserId(new UserId());
        Store store = storeRequest.toStore();
        given(storeDao.existsByStoreUserId(any())).willReturn(false);
        given(storeRepository.save(any())).willReturn(store);

        // when
        StoreResponse storeResponse = storeService.addStore(storeRequest);

        // then
        assertThat(store.getStoreId()).isEqualTo(storeResponse.getStoreId());
    }

    @DisplayName("User는 이미 Store를 하나 만들었으면 에외를 던진다.")
    @Test
    void addStoreFailed() {
        // given
        StoreRequest storeRequest = new StoreRequest("test");
        storeRequest.setUserId(new UserId());
        Store store = storeRequest.toStore();
        given(storeDao.existsByStoreUserId(any())).willReturn(true);

        // when,then
        assertThatThrownBy(() -> storeService.addStore(storeRequest)).isInstanceOf(
                StoreAlreadyExistsError.class);

    }
}
