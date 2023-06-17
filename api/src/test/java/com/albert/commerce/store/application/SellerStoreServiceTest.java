package com.albert.commerce.store.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import com.albert.commerce.common.model.SequenceGenerator;
import com.albert.commerce.common.model.SequenceGeneratorImpl;
import com.albert.commerce.store.command.application.NewStoreRequest;
import com.albert.commerce.store.command.application.SellerStoreResponse;
import com.albert.commerce.store.command.application.SellerStoreService;
import com.albert.commerce.store.command.domain.Store;
import com.albert.commerce.store.command.domain.StoreId;
import com.albert.commerce.store.command.domain.StoreRepository;
import com.albert.commerce.store.command.domain.StoreUserId;
import com.albert.commerce.user.command.domain.UserId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


class SellerStoreServiceTest {

    private SellerStoreService sellerStoreService;
    private StoreRepository storeRepository;
    private SequenceGenerator sequenceGenerator;

    @BeforeEach
    void setUserService() {
        storeRepository = mock(StoreRepository.class);
        sequenceGenerator = new SequenceGeneratorImpl();
        sellerStoreService = new SellerStoreService(storeRepository, sequenceGenerator);
    }

    @DisplayName("User는 Store를 하나 만들 수 있습니다.")
    @Test
    void addStoreSuccess() {
        // given
        NewStoreRequest newStoreRequest = new NewStoreRequest("test");
        UserId userId = UserId.from(sequenceGenerator.generate());
        StoreId storeId = new StoreId(sequenceGenerator.generate());
        Store store = new Store(storeId, newStoreRequest.getStoreName(), StoreUserId.from(userId));
        given(storeRepository.save(any())).willReturn(store);

        // when
        SellerStoreResponse sellerStoreResponse = sellerStoreService.createStore(newStoreRequest,
                userId);

        // then
        assertThat(store.getStoreId()).isEqualTo(sellerStoreResponse.getStoreId());
    }

}
