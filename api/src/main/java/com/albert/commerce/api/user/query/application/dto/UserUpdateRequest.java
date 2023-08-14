package com.albert.commerce.api.user.query.application.dto;

import com.albert.commerce.common.domain.DomainId;
import java.time.LocalDate;
import lombok.Builder;

public class UserUpdateRequest {

    private final DomainId userId;
    private final String address;
    private final String nickname;
    private final LocalDate dateOfBirth;
    private final String phoneNumber;

    @Builder
    private UserUpdateRequest(DomainId userId, String address, String nickname, LocalDate dateOfBirth,
            String phoneNumber) {
        this.userId = userId;
        this.address = address;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
    }

    public DomainId getUserId() {
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
