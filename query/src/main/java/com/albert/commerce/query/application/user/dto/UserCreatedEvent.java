package com.albert.commerce.query.application.user.dto;

import com.albert.commerce.query.domain.user.Role;
import com.albert.commerce.query.domain.user.UserId;
import java.time.LocalDate;

public record UserCreatedEvent(
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
