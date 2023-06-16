package com.albert.commerce.user.query.application;

import java.time.LocalDate;

public record UserProfileRequest(
        String nickname,
        LocalDate dateOfBirth,
        String phoneNumber,
        String address) {

}
