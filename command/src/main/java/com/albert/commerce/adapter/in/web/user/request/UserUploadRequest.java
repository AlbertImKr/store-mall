package com.albert.commerce.adapter.in.web.user.request;

import java.time.LocalDate;

public record UserUploadRequest(
        String nickname,
        LocalDate dateOfBirth,
        String phoneNumber,
        String address
) {

}
