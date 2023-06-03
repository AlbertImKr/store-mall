package com.albert.commerce.user;

import com.albert.commerce.user.exception.EmailNotFoundException;
import com.albert.commerce.user.security.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public void login(User user) {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        new CustomUserDetails(user),
                        user.getPassword(),
                        List.of(new SimpleGrantedAuthority(user.getRole().getKey())));

        SecurityContextHolder.getContext().setAuthentication(token);
    }

    public User findByEmail(String email) {
        return userRepository
                .findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException("이메일 존재하지 않습니다"));
    }
}
