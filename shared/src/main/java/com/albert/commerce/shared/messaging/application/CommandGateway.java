package com.albert.commerce.shared.messaging.application;

import static com.albert.commerce.shared.config.messaging.command.CommandMessageConfig.COMMAND_CHANNEL;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = COMMAND_CHANNEL)
public interface CommandGateway {

    <T> T request(Command command);
}
