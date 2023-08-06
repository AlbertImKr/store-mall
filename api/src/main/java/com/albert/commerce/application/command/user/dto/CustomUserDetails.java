package com.albert.commerce.application.command.user.dto;

import com.albert.commerce.domain.query.user.UserData;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class CustomUserDetails extends User {

    private final UserData user;

    public CustomUserDetails(UserData user) {
        super(user.getEmail(), null,
                List.of(new SimpleGrantedAuthority(user.getRole().getKey())));
        this.user = user;
    }
}
