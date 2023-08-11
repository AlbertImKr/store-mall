package com.albert.commerce.api.user.query.domain;

import com.albert.commerce.api.user.command.domain.Role;
import com.albert.commerce.common.domain.DomainId;
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
@Table(name = "user_query")
public class UserData {

    @EmbeddedId
    @AttributeOverride(name = "value", column = @Column(name = "user_id"))
    private DomainId userId;
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
    private UserData(DomainId userId, String nickname, String email, Role role, LocalDate dateOfBirth,
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

    public DomainId getUserId() {
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
