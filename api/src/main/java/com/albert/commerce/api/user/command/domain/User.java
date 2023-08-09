package com.albert.commerce.api.user.command.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private UserId userId;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = true)
    private LocalDate dateOfBirth;
    @Column(nullable = true)
    private String phoneNumber;
    @Column(nullable = true)
    private String address;
    @Column(nullable = false)
    private boolean isActive;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime createdTime;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    protected LocalDateTime updateTime;

    @Builder
    private User(UserId userId, String nickname, String email, Role role, LocalDate dateOfBirth,
            String phoneNumber, String address) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public static User createByEmail(String email) {
        return User.builder()
                .nickname("user")
                .email(email)
                .role(Role.USER)
                .build();
    }

    public void updateId(UserId userId) {
        this.userId = userId;
        this.createdTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }
}
