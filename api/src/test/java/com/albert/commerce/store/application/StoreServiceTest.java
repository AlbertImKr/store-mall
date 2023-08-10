package com.albert.commerce.store.application;

import com.albert.commerce.api.store.command.application.StoreService;
import com.albert.commerce.api.store.query.application.StoreFacade;
import com.albert.commerce.api.user.command.application.UserService;
import com.albert.commerce.api.user.query.domain.UserDao;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class StoreServiceTest {

    private static final String TEST_STORE_NAME = "testStoreName";
    private static final String TEST_EMAIL = "test@email.com";
    private static final String TEST_OWNER = "testOwner";
    private static final String TEST_PHONE_NUMBER = "01011001100";
    private static final String TEST_ADDRESS = "testAddress";
    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreFacade storeFacade;

    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @DisplayName("User는 Store를 하나 만들 수 있습니다.")
    @Test
    void addStoreSuccess() {
        // given
//        NewStoreRequest newStoreRequest = NewStoreRequest.builder()
//                .storeName(TEST_STORE_NAME)
//                .email(TEST_EMAIL)
//                .ownerName(TEST_OWNER)
//                .phoneNumber(TEST_PHONE_NUMBER)
//                .address(TEST_ADDRESS)
//                .build();
//        String email = "seller@email.com";
//        userService.createByEmail(email);
//        UserData user = userDao.findByEmail(email).orElseThrow(UserNotFoundException::new);
//
//        // when
//        StoreId storeId = sellerStoreService.createStore(user.getEmail(), newStoreRequest);
//
//        // then
//        StoreData store = storeFacade.getStoreById(storeId);
//        assertThat(store.getStoreName()).isEqualTo(TEST_STORE_NAME);
//        assertThat(store.getEmail()).isEqualTo(TEST_EMAIL);
//        assertThat(store.getOwnerName()).isEqualTo(TEST_OWNER);
//        assertThat(store.getPhoneNumber()).isEqualTo(TEST_PHONE_NUMBER);
//        assertThat(store.getAddress()).isEqualTo(TEST_ADDRESS);
    }

}
