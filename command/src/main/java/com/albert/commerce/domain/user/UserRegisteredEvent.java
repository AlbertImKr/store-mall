package com.albert.commerce.domain.user;

import com.albert.commerce.domain.event.DomainEvent;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserRegisteredEvent extends DomainEvent {

    private final UserId userId;
    private final String nickname;
    private final String email;
    private final Role role;
    private final LocalDate dateOfBirth;
    private final String phoneNumber;
    private final String address;
    private final boolean isActive;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private final LocalDateTime createdTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private final LocalDateTime updatedTime;

    public UserRegisteredEvent(UserId userId, String nickname, String email, Role role, LocalDate dateOfBirth,
            String phoneNumber, String address, boolean isActive, LocalDateTime createdTime,
            LocalDateTime updatedTime) {
        this.userId = userId;
        this.nickname = nickname;
        this.email = email;
        this.role = role;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.isActive = isActive;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
