package com.albert.commerce.domain.user;

import com.albert.commerce.domain.event.DomainEvent;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class UserUpdatedEvent extends DomainEvent {

    private final UserId userId;
    private final String address;
    private final String nickname;
    private final LocalDate dateOfBirth;
    private final String phoneNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMddHHmmss")
    private final LocalDateTime updatedTime;

    public UserUpdatedEvent(UserId userId, String address, String nickname, LocalDate dateOfBirth,
            String phoneNumber, LocalDateTime updatedTime) {
        this.userId = userId;
        this.address = address;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.updatedTime = updatedTime;
    }
}
