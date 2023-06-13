package com.albert.commerce.user.command.domain;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user")
public class User {

    @EmbeddedId
    private UserId id;
    private String nickname;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public User(String nickname, String email, Role role) {
        this.id = new UserId();
        this.nickname = nickname;
        this.email = email;
        this.role = role;
    }
}
