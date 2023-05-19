package com.albert.commerce.user;

import com.albert.commerce.user.dto.JoinRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    UserService userService;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUserService() {
        userService = new UserService(userRepository, new BCryptPasswordEncoder());
    }


    @DisplayName("joinRequest 를 받고 userRepository 를 통해 User 를 저장한다")
    @Test
    void saveUser() {
        // given
        JoinRequest joinRequest = mock(JoinRequest.class);
        User user = mock(User.class);
        given(joinRequest.toUser(any(PasswordEncoder.class), any(EncryptionAlgorithm.class), any(Role.class)))
                .willReturn(user);

        // when
        userService.save(joinRequest);

        // then
        verify(userRepository).save(user);
    }

    @DisplayName("User 정보를 SecurityContext에 저장하여 로그인을 구현한다")
    @Test
    void login() {
        // given
        User user = new User("jack", "testPassword", "jack@email.com", EncryptionAlgorithm.BCRYPT, Role.USER);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();

        // when
        userService.login(user);

        // then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
    }

}
