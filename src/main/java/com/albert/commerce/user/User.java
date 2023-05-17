package com.albert.commerce.user;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.concurrent.atomic.AtomicLong;

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

    @Builder
    public User(String nickname, String password, String email) {
        this.id = idGenerator.incrementAndGet();
        this.nickname = nickname;
        this.password = password;
        this.email = email;
    }
}
