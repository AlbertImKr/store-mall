package com.albert.commerce.user.command.application;

import java.time.LocalDate;

public record UserProfileRequest(
        String nickname,
        LocalDate dateOfBirth,
        String phoneNumber,
        String address) {

}
