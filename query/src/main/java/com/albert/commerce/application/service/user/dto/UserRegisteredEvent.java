package com.albert.commerce.application.service.user.dto;

import com.albert.commerce.domain.user.Role;
import com.albert.commerce.domain.user.UserId;
import java.time.LocalDate;

public record UserRegisteredEvent(
        UserId userId,
        String nickname,
        String email,
        Role role,
        LocalDate dateOfBirth,
        String phoneNumber,
        String address,
        boolean isActive
) {

}
