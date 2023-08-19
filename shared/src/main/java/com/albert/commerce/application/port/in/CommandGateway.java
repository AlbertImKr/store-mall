package com.albert.commerce.application.port.in;

import static com.albert.commerce.config.messaging.command.CommandMessageConfig.COMMAND_CHANNEL;

import com.albert.commerce.application.service.Command;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = COMMAND_CHANNEL)
public interface CommandGateway {

    <T> T request(Command command);
}
