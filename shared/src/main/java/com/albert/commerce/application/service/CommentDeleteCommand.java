package com.albert.commerce.application.service;

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
