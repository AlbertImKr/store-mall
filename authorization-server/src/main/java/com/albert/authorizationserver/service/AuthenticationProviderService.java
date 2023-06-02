package com.albert.authorizationserver.service;

import com.albert.authorizationserver.exception.EmailTypeMismatchException;
import com.albert.authorizationserver.exception.PasswordTypeMismatchException;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthenticationProviderService implements AuthenticationProvider {

    private static final String EMAIL_PATTERN = "^(.+)@(.+)$";
    private static final String PASSWORD_PATTERN =
            "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$";
    private final CustomUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        String username = authentication.getName();
        validateEmailType(username);
        String password = authentication.getCredentials().toString();
        validatePasswordType(password);

        CustomUserDetails user = userDetailsService.loadUserByUsername(username);

        return switch (user.getAlgorithm()) {
            case BCRYPT -> checkPassword(user, password, bCryptPasswordEncoder);
        };
    }

    private void validatePasswordType(String password) {
        if (!Pattern.matches(PASSWORD_PATTERN, password)) {
            throw new PasswordTypeMismatchException("Password Type가 맞지 않습니다");
        }
    }

    private void validateEmailType(String email) {
        if (!Pattern.matches(EMAIL_PATTERN, email)) {
            throw new EmailTypeMismatchException("Email Type가 맞지 않습니다");
        }
    }

    private Authentication checkPassword(
            CustomUserDetails user, String password, PasswordEncoder passwordEncoder) {
        if (passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword(), user.getAuthorities());
        } else {
            throw new BadCredentialsException("비번이 일치하지 않습니다.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
