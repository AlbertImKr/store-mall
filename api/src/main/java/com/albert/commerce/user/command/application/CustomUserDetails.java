package com.albert.commerce.user.command.application;

import com.albert.commerce.user.query.application.UserInfoResponse;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class CustomUserDetails extends User {

    private final UserInfoResponse userInfoResponse;

    public CustomUserDetails(UserInfoResponse userInfoResponse) {
        super(userInfoResponse.getEmail(), null,
                List.of(new SimpleGrantedAuthority(userInfoResponse.getRole().getKey())));
        this.userInfoResponse = userInfoResponse;
    }
}
