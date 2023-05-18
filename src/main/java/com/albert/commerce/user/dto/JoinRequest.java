package com.albert.commerce.user.dto;

import com.albert.commerce.user.EncryptionAlgorithm;
import com.albert.commerce.user.Role;
import com.albert.commerce.user.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@RequiredArgsConstructor
public class JoinRequest {
    private final String email;
    private final String nickname;
    private final String password;
    private final String confirmPassword;


    public User toUser(PasswordEncoder passwordEncoder, EncryptionAlgorithm algorithm, Role role) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .role(role)
                .algorithm(algorithm)
                .build();
    }
}
