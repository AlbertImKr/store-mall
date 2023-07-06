package com.albert.commerce.user.command.application.dto;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record UserProfileRequest(
        @Size(min = 2, max = 60) String nickname,
        @Past LocalDate dateOfBirth,
        @Pattern(regexp = "^[0-9]{11}$") String phoneNumber,
        @Size(min = 5, max = 100) String address) {

}
