package com.albert.commerce.user;

import com.albert.commerce.user.exception.EmailTypeMismatchException;
import com.albert.commerce.user.exception.PasswordTypeMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class AuthenticationProviderService implements AuthenticationProvider {

    private final CustomUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final SCryptPasswordEncoder sCryptPasswordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        validEmailType(username);
        String password = authentication.getCredentials().toString();
        validPasswordType(password);

        CustomUserDetails user = userDetailsService.loadUserByUsername(username);

        switch (user.getUser().getAlgorithm()) {
            case BCRYPT:
                return checkPassword(user, password, bCryptPasswordEncoder);
            case SCRYPT:
                return checkPassword(user, password, sCryptPasswordEncoder);
        }
        throw new BadCredentialsException("Bad credentials");
    }

    private void validPasswordType(String password) {
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=\\S+$).{8,20}$";
        if (!Pattern.matches(regex, password)) {
            throw new PasswordTypeMismatchException("Password Type가 맞지 않습니다");
        }
    }

    private void validEmailType(String email) {
        String regex = "^(.+)@(.+)$";
        if (!Pattern.matches(regex, email)) {
            throw new EmailTypeMismatchException("Email Type가 맞지 않습니다");
        }
    }

    private Authentication checkPassword(CustomUserDetails user, String password, PasswordEncoder passwordEncoder) {
        if (passwordEncoder.matches(password, user.getPassword())) {
            return new UsernamePasswordAuthenticationToken(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities()
            );
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
