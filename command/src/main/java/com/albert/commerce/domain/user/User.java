package com.albert.commerce.domain.user;

import com.albert.commerce.domain.event.Events;
import jakarta.persistence.AttributeOverride;
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
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class User {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    private UserId userId;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column
    private LocalDate dateOfBirth;
    @Column
    private String phoneNumber;
    @Column
    private String address;
    @Column(nullable = false)
    private boolean isActive;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    @Builder
    private User(UserId userId, String nickname, String email, Role role, LocalDate dateOfBirth,
            String phoneNumber, String address, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
        Events.raise(toUserRegisteredEvent());
    }

    public static User createByEmail(UserId userId, String email, LocalDateTime createdTime) {
        return User.builder()
                .userId(userId)
                .nickname(email)
                .email(email)
                .role(Role.USER)
                .createdTime(createdTime)
                .build();
    }

    public void update(String address, String nickname, LocalDate dateOfBirth, String phoneNumber,
            LocalDateTime updatedTime) {
        this.address = address;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.updatedTime = updatedTime;
        Events.raise(toUserUpdateEvent());
    }

    public UserId getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    private UserRegisteredEvent toUserRegisteredEvent() {
        return new UserRegisteredEvent(
                userId,
                nickname,
                email,
                role,
                dateOfBirth,
                phoneNumber,
                address,
                isActive,
                createdTime,
                updatedTime
        );
    }

    private UserUpdatedEvent toUserUpdateEvent() {
        return new UserUpdatedEvent(
                userId,
                address,
                nickname,
                dateOfBirth,
                phoneNumber,
                updatedTime
        );
    }

}
