package com.albert.commerce.user;

import com.albert.commerce.user.dto.JoinRequest;
import com.albert.commerce.user.exception.EmailAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    public static final String TEST_EMAIL = "jack@email.com";
    UserService userService;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUserService() {
        userService = new UserService(userRepository, new BCryptPasswordEncoder());
    }


    @DisplayName("joinRequest를 받고 userRepository를 통해 User를 저장한다")
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
        User user = new User("jack", "testPassword", TEST_EMAIL, EncryptionAlgorithm.BCRYPT, Role.USER);
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNull();

        // when
        userService.login(user);

        // then
        assertThat(SecurityContextHolder.getContext().getAuthentication()).isNotNull();
    }

    @DisplayName("email로 user 조회 시 Repository를 통하여 조회하고 user가 존재하면 user를 리턴한다")
    @Test
    void findByEmailSuccess() {
        // given
        User user = mock(User.class);
        given(userRepository.findByEmail(TEST_EMAIL)).willReturn(Optional.of(user));

        // when
        User findedUser = userService.findByEmail(TEST_EMAIL);

        // then
        assertThat(findedUser).isEqualTo(user);
        verify(userRepository).findByEmail(TEST_EMAIL);
    }

    @DisplayName("email로 user 조회 시 Repository를 통하여 조회하고 값이 없으면 예외를 던진다")
    @Test
    void findByEmailFailed() {
        // given
        given(userRepository.findByEmail(TEST_EMAIL)).willReturn(Optional.empty());

        // when,then
        assertThatThrownBy(() -> userService.findByEmail(TEST_EMAIL));
    }

    @DisplayName("중복된 email를 저장시 예외를 던진다")
    @Test
    void saveSameEmailThrowException() {
        // given
        JoinRequest joinRequest = mock(JoinRequest.class);
        User user = mock(User.class);
        given(joinRequest.toUser(any(PasswordEncoder.class), any(EncryptionAlgorithm.class), any(Role.class)))
                .willReturn(user);
        userService.save(joinRequest);
        given(userRepository.findByEmail(any())).willReturn(Optional.of(user));

        // when ,then
        assertThatThrownBy(() -> userService.save(joinRequest)).isInstanceOf(EmailAlreadyExistsException.class);
    }
}
