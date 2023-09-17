package com.albert.commerce.config.messaging.command;

import com.albert.commerce.application.port.in.CommandGateway;
import com.albert.commerce.application.service.Command;
import java.util.Set;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.web.context.support.GenericWebApplicationContext;

@Profile("messaging")
@Configuration
@IntegrationComponentScan(basePackageClasses = {CommandGateway.class})
@ConditionalOnProperty(name = "commerce.messaging.base-package.command")
public class CommandMessageConfig {

    public static final String COMMAND_CHANNEL = "commandChannel";

    @Value("${commerce.messaging.base-package.command}")
    private String commandBasePackage;

    @Bean(COMMAND_CHANNEL)
    public DirectChannel commandChannel() {
        return new DirectChannel();
    }

    @Bean
    public IntegrationFlow commandRoutingFlow() {
        return IntegrationFlow.from(COMMAND_CHANNEL)
                .route(command -> command.getClass().getSimpleName())
                .get();
    }

    @Bean
    public CommandClassResolver commandClassResolver() {
        Reflections reflections = new Reflections(commandBasePackage);
        Set<Class<? extends Command>> commandClasses = reflections.getSubTypesOf(Command.class);
        return new CommandClassResolver(commandClasses);
    }

    @Bean
    public boolean registerCommand(
            GenericWebApplicationContext context,
            TaskExecutor messageTaskExecutor,
            CommandClassResolver commandClassResolver
    ) {
        for (String className : commandClassResolver.getNames()) {
            PublishSubscribeChannel channel = new PublishSubscribeChannel(messageTaskExecutor);
            context.registerBean(className, PublishSubscribeChannel.class, () -> channel);
        }
        return true;
    }
}
