package com.albert.commerce.application.service.comment;

import com.albert.commerce.application.service.Command;

public class CommentDeleteCommand extends Command {

    private final String userEmail;
    private final String commandId;

    public CommentDeleteCommand(String userEmail, String commandId) {
        this.userEmail = userEmail;
        this.commandId = commandId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getCommandId() {
        return commandId;
    }
}
