package com.albert.commerce.user.command.domain;

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
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String nickname, String email, Role role) {
        this.id = idGenerator.incrementAndGet();
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }
}
