package com.albert.commerce.api.user.command.domain;

import com.albert.commerce.common.domain.DomainId;
import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import java.time.LocalDate;
import lombok.Builder;

public class UserCreatedEvent extends DomainEvent {

    private final DomainId userId;
    private final String nickname;
    private final String email;
    private final Role role;
    private final LocalDate dateOfBirth;
    private final String phoneNumber;
    private final String address;
    private final boolean isActive;

    @Builder
    private UserCreatedEvent(DomainId userId, String nickname, String email, Role role, LocalDate dateOfBirth,
            String phoneNumber, String address, boolean isActive) {
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
