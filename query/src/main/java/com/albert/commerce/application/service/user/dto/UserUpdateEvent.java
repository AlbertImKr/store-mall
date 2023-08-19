package com.albert.commerce.application.service.user.dto;

import com.albert.commerce.domain.user.UserId;
import java.time.LocalDate;

public record UserUpdateEvent(
        UserId userId,
        String address,
        String nickname,
        LocalDate dateOfBirth,
        String phoneNumber
) {

}
