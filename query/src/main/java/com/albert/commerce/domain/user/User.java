package com.albert.commerce.domain.user;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDate;
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

    @Builder
    private User(UserId userId, String nickname, String email, Role role, LocalDate dateOfBirth,
            String phoneNumber,
            String address, boolean isActive) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isActive = isActive;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public boolean isActive() {
        return isActive;
    }

    public void update(LocalDate dateOfBirth, String address, String nickname, String phoneNumber) {
        this.dateOfBirth = dateOfBirth;
        this.address = address;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }
}
