package com.albert.commerce.application.service;

import com.albert.commerce.application.service.user.UserUploadCommand;

import java.time.LocalDate;

public class ApplicationFixture {

    public static final String USER_EMAIL = "test@email.com";
    public static final String UPDATE_USER_NICKNAME = "nick";
    public static final LocalDate UPDATE_BIRTH_DAY = LocalDate.of(2023, 9, 30);
    public static final String UPDATE_PHONE_NUMBER = "010-2222-2222";
    public static final String UPDATE_ADDRESS = "서울시 강남구";

    public static UserUploadCommand getUserUploadCommand() {
        return new UserUploadCommand(USER_EMAIL, UPDATE_USER_NICKNAME, UPDATE_BIRTH_DAY, UPDATE_PHONE_NUMBER, UPDATE_ADDRESS);
    }
}
