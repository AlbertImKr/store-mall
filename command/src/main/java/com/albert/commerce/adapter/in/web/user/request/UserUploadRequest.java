package com.albert.commerce.adapter.in.web.user.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record UserUploadRequest(
        @Size(min = 2, max = 60)
        String nickname,
        @JsonFormat(pattern = "yyyy-MM-dd")
        @Past
        LocalDate dateOfBirth,
        @Pattern(regexp = "^[0-9]{11}$")
        String phoneNumber,
        @Size(min = 5, max = 100)
        String address
) {

}
