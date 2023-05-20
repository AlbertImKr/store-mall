package com.albert.commerce.user.dto;

import com.albert.commerce.user.EncryptionAlgorithm;
import com.albert.commerce.user.Role;
import com.albert.commerce.user.User;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.Size;

@PasswordMatches
@Getter
public class JoinRequest {
    @Email
    private String email;
    @Size(min = 3, max = 10)
    private String nickname;
    @PasswordPattern
    private String password;
    @PasswordPattern
    private String confirmPassword;

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
