package com.albert.commerce.application.service;

public class CommentUpdateCommand extends Command {

    private final String userEmail;
    private final String commandId;
    private final String detail;

    public CommentUpdateCommand(String userEmail, String commandId, String detail) {
        this.userEmail = userEmail;
        this.commandId = commandId;
        this.detail = detail;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getCommandId() {
        return commandId;
    }

    public String getDetail() {
        return detail;
    }
}
