package com.albert.commerce.application.service.user;

import com.albert.commerce.adapter.in.web.request.CustomUserDetails;
import com.albert.commerce.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        if (!userService.exists(email)) {
            userService.createByEmail(email);
        }
        User user = userService.getUserByEmail(email);
        return new CustomUserDetails(user);
    }
}
