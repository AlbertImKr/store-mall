package com.albert.commerce.shared.messaging.application;

import java.time.LocalDate;

public class UserUpdateCommand extends Command {

    private final String userEmail;
    private final String nickname;
    private final LocalDate dateOfBirth;
    private final String phoneNumber;
    private final String address;

    public UserUpdateCommand(String userEmail, String nickname, LocalDate dateOfBirth, String phoneNumber,
            String address) {
        this.userEmail = userEmail;
        this.nickname = nickname;
        this.dateOfBirth = dateOfBirth;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public String getUserEmail() {
        return userEmail;
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

    public String getAddress() {
        return address;
    }
}
