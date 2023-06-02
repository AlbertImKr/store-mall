package com.albert.authorizationserver.dto;


import com.albert.authorizationserver.model.EncryptionAlgorithm;
import com.albert.authorizationserver.model.Role;
import com.albert.authorizationserver.model.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@PasswordMatches
@Getter
@NoArgsConstructor
public class JoinRequest {

    @Email
    private String email;

    @Size(min = 3, max = 10)
    private String nickname;

    @PasswordPattern
    private String password;
    @PasswordPattern
    private String confirmPassword;

    @Builder
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
