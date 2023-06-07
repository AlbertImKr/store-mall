package com.albert.authorizationserver.service;

import com.albert.authorizationserver.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public CustomUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        return new CustomUserDetails(user);
    }

    public CustomUserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        return new CustomUserDetails(user);
    }
}
