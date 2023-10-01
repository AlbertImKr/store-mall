package com.albert.commerce.application.service.user;

import com.albert.commerce.application.port.out.UserRepository;
import com.albert.commerce.exception.error.UserNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.albert.commerce.application.service.ApplicationFixture.USER_EMAIL;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @DisplayName("이메일로 유저를 조회시 유저가 없으면 예외를 던진다.")
    @Test
    void getUserByEmail() {
        // given
        given(userRepository.findByEmail(USER_EMAIL))
                .willReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> userService.getUserByEmail(USER_EMAIL))
                .isInstanceOf(UserNotFoundException.class);
    }
}
