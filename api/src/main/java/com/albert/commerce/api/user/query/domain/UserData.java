package com.albert.commerce.api.user.query.domain;

import com.albert.commerce.api.user.command.domain.Role;
import com.albert.commerce.api.user.command.domain.UserId;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user")
public class UserData {

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
}
