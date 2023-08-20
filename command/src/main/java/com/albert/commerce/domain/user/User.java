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
                .nickname(email)
                .email(email)
                .role(Role.USER)
                .build();
    }

    public void updateId(UserId userId, LocalDateTime createdTime, LocalDateTime updateTime) {
        this.userId = userId;
        this.createdTime = createdTime;
        this.updatedTime = updateTime;
        Events.raise(toUserRegisteredEvent());
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
        return UserRegisteredEvent.builder()
                .userId(userId)
                .nickname(nickname)
                .email(email)
                .role(role)
                .dateOfBirth(dateOfBirth)
                .phoneNumber(phoneNumber)
                .address(address)
                .isActive(isActive)
                .createdTime(createdTime)
                .updatedTime(updatedTime)
                .build();
    }

    private UserUpdatedEvent toUserUpdateEvent() {
        return UserUpdatedEvent.builder()
                .userId(userId)
                .address(address)
                .nickname(nickname)
                .dateOfBirth(dateOfBirth)
                .phoneNumber(phoneNumber)
                .updatedTime(updatedTime)
                .build();
    }
}
