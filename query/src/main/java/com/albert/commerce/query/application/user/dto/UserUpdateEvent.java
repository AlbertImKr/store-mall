package com.albert.commerce.query.application.user.dto;

import com.albert.commerce.query.domain.user.UserId;
import java.time.LocalDate;

public record UserUpdateEvent(
        UserId userId,
        String address,
        String nickname,
        LocalDate dateOfBirth,
        String phoneNumber
) {

}
