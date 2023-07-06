package com.albert.commerce.user.command.application.dto;

import java.util.List;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

@Getter
public class CustomUserDetails extends User {

    private final com.albert.commerce.user.command.domain.User user;

    public CustomUserDetails(com.albert.commerce.user.command.domain.User user) {
        super(user.getEmail(), null,
                List.of(new SimpleGrantedAuthority(user.getRole().getKey())));
        this.user = user;
    }
}
