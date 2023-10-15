package com.albert.commerce.application.service.store;

import com.albert.commerce.application.service.Command;

public class StoreDeleteCommand extends Command {

    private final String userEmail;

    public StoreDeleteCommand(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserEmail() {
        return userEmail;
    }
}
