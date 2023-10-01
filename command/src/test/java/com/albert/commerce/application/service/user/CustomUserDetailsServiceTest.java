package com.albert.commerce.application.service.user;

import com.albert.commerce.application.port.out.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static com.albert.commerce.application.service.ApplicationFixture.USER_EMAIL;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @InjectMocks
    CustomUserDetailsService customUserDetailsService;

    @Mock
    UserRepository userRepository;

    @DisplayName("이메일로 UserDetails를 생성시 DB에 해당 이메일이 존재하지 않으면 예외를 던진다")
    @Test
    void loadUserByUsername() {
        // given
        given(userRepository.findByEmail(USER_EMAIL)).willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> customUserDetailsService.loadUserByUsername(USER_EMAIL))
                // then
                .isInstanceOf(UsernameNotFoundException.class);
    }
}
