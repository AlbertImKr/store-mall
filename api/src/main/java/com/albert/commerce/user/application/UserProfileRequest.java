package com.albert.commerce.user.application;

import java.time.LocalDate;

public record UserProfileRequest(
        String nickname,
        LocalDate dateOfBirth,
        String phoneNumber,
        String address) {

}
