package com.albert.commerce.application.service.auth;

import com.albert.commerce.application.service.Command;

public class LoginCommand extends Command {

    private final String email;

    public LoginCommand(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
