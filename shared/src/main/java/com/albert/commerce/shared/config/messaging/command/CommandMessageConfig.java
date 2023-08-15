package com.albert.commerce.shared.config.messaging.command;

import com.albert.commerce.shared.messaging.application.Command;
import com.albert.commerce.shared.messaging.application.CommandGateway;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.ExecutorChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.stereotype.Component;

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

    @Component
    private static class CommandExecutorChannelRegistry {

        private final CommandClassResolver commandClassResolver;
        private final Map<String, ExecutorChannel> commandChannels = new ConcurrentHashMap<>();

        public CommandExecutorChannelRegistry(CommandClassResolver commandClassResolver) {
            this.commandClassResolver = commandClassResolver;
        }

        public void registerCommandChannels(TaskExecutor messageTaskExecutor) {
            Set<String> commandNames = commandClassResolver.getNames();

            for (String commandName : commandNames) {
                ExecutorChannel executorChannel = new ExecutorChannel(messageTaskExecutor);
                commandChannels.put(commandName, executorChannel);
            }
        }
    }
}
