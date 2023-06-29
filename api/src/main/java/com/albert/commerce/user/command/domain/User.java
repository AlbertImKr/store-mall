package com.albert.commerce.user.command.domain;

import static com.albert.commerce.user.command.domain.Role.USER;

import com.albert.commerce.common.infra.persistence.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    @EmbeddedId
    private UserId id;
    @Column(nullable = true)
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

    @Builder
    public User(UserId id, String nickname, String email, Role role, LocalDate dateOfBirth,
            String phoneNumber, String address, boolean isActive) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isActive = isActive;
    }

    public static User createByEmail(String email, UserId userId) {
        return User.builder()
                .nickname("user")
                .id(userId)
                .email(email)
                .role(USER)
                .isActive(false)
                .build();
    }

}
