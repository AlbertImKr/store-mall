package com.albert.commerce.user.command.application;

import com.albert.commerce.user.query.application.UserInfoResponse;
import com.albert.commerce.user.query.domain.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserDao userDao;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserInfoResponse userInfoResponse = userDao.findUserProfileByEmail(email);
        if (userInfoResponse == null) {
            throw new UsernameNotFoundException(email);
        }
        return new CustomUserDetails(userInfoResponse);
    }
}
