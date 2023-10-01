package com.albert.commerce.domain;

import com.albert.commerce.domain.user.UserId;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DomainFixture {

    public static final String USER_EMAIL = "test@email.com";
    public static final String UPDATE_USER_NICKNAME = "nick";
    public static final LocalDate UPDATE_BIRTH_DAY = LocalDate.of(1999, 9, 30);
    public static final String UPDATE_PHONE_NUMBER = "010-2222-2222";
    public static final String UPDATE_ADDRESS = "서울시 강남구";
    public static final String USER_ID = "1";

    public static UserId getUserId() {
        return UserId.from(USER_ID);
    }

    public static String getUpdatedAddress() {
        return UPDATE_ADDRESS;
    }

    public static String getUpdatedNickname() {
        return UPDATE_USER_NICKNAME;
    }

    public static String getUpdatedPhoneNumber() {
        return UPDATE_PHONE_NUMBER;
    }

    public static LocalDate getUpdatedDateOfBirth() {
        return UPDATE_BIRTH_DAY;
    }

    public static LocalDateTime getUpdatedTime() {
        return LocalDateTime.of(2023, 9, 30, 0, 0);
    }

    public static LocalDateTime getCreatedTime() {
        return LocalDateTime.of(2023, 9, 29, 0, 0);
    }
}
