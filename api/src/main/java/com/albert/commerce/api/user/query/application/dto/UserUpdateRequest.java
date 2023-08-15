package com.albert.commerce.api.user.query.application.dto;

import com.albert.commerce.api.user.command.domain.UserId;
import java.time.LocalDate;
import lombok.Builder;

public class UserUpdateRequest {

    private final UserId userId;
    private final String address;
    private final String nickname;
    private final LocalDate dateOfBirth;
    private final String phoneNumber;

    @Builder
    private UserUpdateRequest(UserId userId, String address, String nickname, LocalDate dateOfBirth,
            String phoneNumber) {
        this.userId = userId;
        this.address = address;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public UserId getUserId() {
        return userId;
    }

    public String getAddress() {
        return address;
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
