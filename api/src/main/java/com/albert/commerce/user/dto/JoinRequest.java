package com.albert.commerce.user.dto;

import com.albert.commerce.user.EncryptionAlgorithm;
import com.albert.commerce.user.Role;
import com.albert.commerce.user.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@PasswordMatches
@Getter
public class JoinRequest {

    @Email
    private final String email;

    @Size(min = 3, max = 10)
    private final String nickname;

    @PasswordPattern
    private final String password;
    @PasswordPattern
    private final String confirmPassword;

    public JoinRequest(String email, String nickname, String password, String confirmPassword) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }


    public User toUser(PasswordEncoder passwordEncoder, EncryptionAlgorithm algorithm, Role role) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .role(role)
                .algorithm(algorithm)
                .build();
    }

    public boolean isSamePassword() {
        return password.equals(confirmPassword);
    }
}
