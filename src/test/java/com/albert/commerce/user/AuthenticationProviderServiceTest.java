package com.albert.commerce.user;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.albert.commerce.user.exception.EmailTypeMismatchException;
import com.albert.commerce.user.exception.PasswordTypeMismatchException;
import com.albert.commerce.user.security.AuthenticationProviderService;
import com.albert.commerce.user.security.CustomUserDetails;
import com.albert.commerce.user.security.CustomUserDetailsService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;

class AuthenticationProviderServiceTest {

    public static final String RIGHT_EMAIL = "jack@email.com";
    public static final String WRONG_EMAIL = "erroremail";
    public static final String RIGHT_8_PASSWORD = "kkkkk!1S";
    public static final String RIGHT_20_PASSWORD = "loooooooooooooong!1S";
    public static final String WRONG_SHORT_7_PASSWORD = "kkkk!1S";
    public static final String WRONG_LONG_21_PASSWORD = "looooooooooooooong!1S";
    public static final String WRONG_NOT_CONTAIN_UPPERCASE_PASSWORD = "kkkkk!1a";
    public static final String WRONG_NOT_CONTAIN_LOWERCASE_PASSWORD = "KKKKK!1A";
    public static final String WRONG_NOT_CONTAIN_SPECIAL_SYMBOLS_PASSWORD = "KKKKKa1A";
    public static final String RIGHT_3_NICKNAME = "kim";

    AuthenticationProviderService authenticationProviderService;
    UserService userService;
    BCryptPasswordEncoder bCryptPasswordEncoder;
    SCryptPasswordEncoder sCryptPasswordEncoder;

    @BeforeEach
    void setAuthenticationProviderService() {
        userService = mock(UserService.class);
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
        sCryptPasswordEncoder = new SCryptPasswordEncoder();
        authenticationProviderService =
                new AuthenticationProviderService(new CustomUserDetailsService(userService),
                        bCryptPasswordEncoder, sCryptPasswordEncoder);
    }

    @DisplayName("email이 email패턴과 일치하지 않을 때 예외를 던진다")
    @Test
    void authenticateFailedByEmailType() {
        // given
        User user = new User(RIGHT_3_NICKNAME, RIGHT_8_PASSWORD, WRONG_EMAIL,
                EncryptionAlgorithm.BCRYPT, Role.USER);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new CustomUserDetails(user),
                user.getPassword(), List.of(new SimpleGrantedAuthority(Role.USER.getKey())));

        // when,then
        assertThatThrownBy(() -> authenticationProviderService.authenticate(authentication))
                .isInstanceOf(EmailTypeMismatchException.class);
        verify(userService, never()).findByEmail(any());
    }

    @DisplayName("password가 password패턴과 일치하지 않을 때 예외를 던진다")
    @ParameterizedTest
    @ValueSource(strings = {WRONG_LONG_21_PASSWORD, WRONG_SHORT_7_PASSWORD,
            WRONG_NOT_CONTAIN_LOWERCASE_PASSWORD,
            WRONG_NOT_CONTAIN_UPPERCASE_PASSWORD, WRONG_NOT_CONTAIN_SPECIAL_SYMBOLS_PASSWORD})
    void authenticateFailedByPasswordType(String password) {
        // given
        User user = new User(RIGHT_3_NICKNAME, password, RIGHT_EMAIL, EncryptionAlgorithm.BCRYPT,
                Role.USER);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new CustomUserDetails(user),
                user.getPassword(), List.of(new SimpleGrantedAuthority(Role.USER.getKey())));

        // when,then
        assertThatThrownBy(() -> authenticationProviderService.authenticate(authentication))
                .isInstanceOf(PasswordTypeMismatchException.class);
        verify(userService, never()).findByEmail(any());
    }

    @DisplayName("user의 password가 일치 하지 않으면 예외를 던진다.")
    @Test
    void authenticateFailedByMisMatchPassword() {
        // given
        User user = new User(RIGHT_3_NICKNAME, RIGHT_8_PASSWORD, RIGHT_EMAIL,
                EncryptionAlgorithm.BCRYPT, Role.USER);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new CustomUserDetails(user),
                user.getPassword(), List.of(new SimpleGrantedAuthority(Role.USER.getKey())));
        User findedUser = mock(User.class);
        given(userService.findByEmail(RIGHT_EMAIL)).willReturn(findedUser);
        given(findedUser.getPassword()).willReturn(RIGHT_20_PASSWORD);
        given(findedUser.getAlgorithm()).willReturn(EncryptionAlgorithm.BCRYPT);

        // when,then
        assertThatThrownBy(() -> authenticationProviderService.authenticate(authentication))
                .isInstanceOf(BadCredentialsException.class);
        verify(userService, times(1)).findByEmail(any());
    }

    @DisplayName("모든 정보 일치하면 정상으로 실행된다")
    @Test
    void authenticateSuccess() {
        // given
        User user = new User(RIGHT_3_NICKNAME, RIGHT_8_PASSWORD, RIGHT_EMAIL,
                EncryptionAlgorithm.SCRYPT, Role.USER);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                new CustomUserDetails(user),
                user.getPassword(), List.of(new SimpleGrantedAuthority(Role.USER.getKey())));
        User findedUser = mock(User.class);
        given(userService.findByEmail(RIGHT_EMAIL)).willReturn(findedUser);
        given(findedUser.getPassword()).willReturn(sCryptPasswordEncoder.encode(RIGHT_8_PASSWORD));
        given(findedUser.getAlgorithm()).willReturn(EncryptionAlgorithm.SCRYPT);
        given(findedUser.getRole()).willReturn(Role.USER);

        // when,then
        assertThatCode(() -> authenticationProviderService.authenticate(
                authentication)).doesNotThrowAnyException();
    }

}
