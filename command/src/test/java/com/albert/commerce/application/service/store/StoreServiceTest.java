package com.albert.commerce.application.service.store;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

import com.albert.commerce.application.port.out.StoreRepository;
import com.albert.commerce.application.service.ApplicationFixture;
import com.albert.commerce.application.service.exception.error.StoreAlreadyExistsException;
import com.albert.commerce.application.service.exception.error.StoreNotFoundException;
import com.albert.commerce.application.service.user.UserService;
import com.albert.commerce.domain.store.StoreId;
import com.albert.commerce.domain.user.UserId;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {

    @InjectMocks
    StoreService storeService;
    @Mock
    StoreRepository storeRepository;
    @Mock
    UserService userService;
    UserId userId;

    @BeforeEach
    void setUp() {
        userId = ApplicationFixture.getUserId();
    }

    @DisplayName("my 스토어를 생성시 이미 my 스토어가 존재하면 예외가 발생한다")
    @Test
    void create_my_store_when_my_store_already_exist_then_exception_occur() {
        // given
        given(userService.getUserIdByEmail(ApplicationFixture.USER_EMAIL))
                .willReturn(userId);
        given(storeRepository.existsByUserId(userId))
                .willReturn(true);
        StoreRegisterCommand storeRegisterCommand = ApplicationFixture.getStoreRegisterCommand();

        // when, then
        assertThatThrownBy(
                () -> storeService.create(storeRegisterCommand)
        ).isInstanceOf(StoreAlreadyExistsException.class);
    }

    @DisplayName("my 스토어를 삭제 진행시 스토어가 존재하지 않으면 예외가 발생한다")
    @Test
    void delete_my_store_when_my_store_does_not_exist_then_exception_occur() {
        // given
        given(userService.getUserIdByEmail(ApplicationFixture.USER_EMAIL))
                .willReturn(userId);
        given(storeRepository.existsByUserId(userId))
                .willReturn(false);
        StoreDeleteCommand storeDeleteCommand = ApplicationFixture.getStoreDeleteCommand();

        // when, then
        assertThatThrownBy(
                () -> storeService.delete(storeDeleteCommand)
        ).isInstanceOf(StoreNotFoundException.class);
    }

    @DisplayName("유저 아이디로 스토어를 조회시 스토어가 존재하지 않으면 예외가 발생한다")
    @Test
    void get_store_by_user_id_when_store_does_not_exist_then_exception_occur() {
        // given
        given(storeRepository.findByUserId(userId))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(
                () -> storeService.getStoreByUserId(userId)
        ).isInstanceOf(StoreNotFoundException.class);
    }

    @DisplayName("유저 아이디로 스토어 아이디를 조회시 스토어가 존재하지 않으면 예외가 발생한다")
    @Test
    void get_store_id_by_user_id_when_store_does_not_exist_then_exception_occur() {
        // given
        given(storeRepository.findByUserId(userId))
                .willReturn(Optional.empty());

        // when, then
        assertThatThrownBy(
                () -> storeService.getStoreIdByUserId(userId)
        ).isInstanceOf(StoreNotFoundException.class);
    }

    @DisplayName("스토어 아이디로 확인 요청시 스토어가 존재 하지 않으면 예외가 발생한다")
    @Test
    void check_store_exist_when_store_does_not_exist_then_exception_occur() {
        // given
        StoreId storeId = ApplicationFixture.getStoreId();
        given(storeRepository.existsById(storeId))
                .willReturn(false);

        // when, then
        assertThatThrownBy(
                () -> storeService.checkExist(storeId)
        ).isInstanceOf(StoreNotFoundException.class);
    }
}
