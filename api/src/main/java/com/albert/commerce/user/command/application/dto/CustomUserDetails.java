package com.albert.commerce.user.command.application.dto;

import com.albert.commerce.user.query.domain.UserData;
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
