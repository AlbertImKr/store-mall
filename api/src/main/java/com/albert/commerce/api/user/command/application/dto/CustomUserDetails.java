package com.albert.commerce.api.user.command.application.dto;

import com.albert.commerce.api.user.command.domain.User;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public class CustomUserDetails extends org.springframework.security.core.userdetails.User {

    private final User user;

    public CustomUserDetails(User user) {
        super(user.getEmail(), null,
                List.of(new SimpleGrantedAuthority(user.getRole().getKey())));
        this.user = user;
    }
}
