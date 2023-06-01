package com.albert.commerce.user;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import java.util.concurrent.atomic.AtomicLong;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User {

    private static final AtomicLong idGenerator = new AtomicLong();

    @Id
    private Long id;
    private String nickname;
    private String password;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Enumerated(EnumType.STRING)
    private EncryptionAlgorithm algorithm;


    @Builder
    public User(String nickname, String password, String email, EncryptionAlgorithm algorithm,
            Role role) {
        this.id = idGenerator.incrementAndGet();
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.algorithm = algorithm;
        this.role = role;
    }
}
