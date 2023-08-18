package com.albert.commerce.shared.config.messaging.domainevent;

import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import java.util.NoSuchElementException;
import java.util.Set;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.support.GenericWebApplicationContext;

@Configuration
public class DomainEventMessageConfig {

    public static final String DOMAIN_EVENT_CHANNEL = "domainEventChannel";

    @Value("${commerce.messaging.base-package.domain-event}")
    private String domainEventBasePackage;

    @Bean
    public DomainEventClassResolver domainEventClassResolver() {
        Reflections reflections = new Reflections(domainEventBasePackage);
        Set<Class<? extends DomainEvent>> domainEventClasses = reflections.getSubTypesOf(DomainEvent.class);
        return new DomainEventClassResolver(domainEventClasses);
    }

    @Bean
    public boolean registerChannel(
            GenericWebApplicationContext context,
            @Qualifier("messageTaskExecutor") TaskExecutor messageTaskExecutor,
            DomainEventClassResolver domainEventClassResolver
    ) {
        for (String className : domainEventClassResolver.getClassNames()) {
            PublishSubscribeChannel channel = new PublishSubscribeChannel(messageTaskExecutor);
            context.registerBean(className, PublishSubscribeChannel.class, () -> channel);
        }
        return true;
    }

    @Bean(DOMAIN_EVENT_CHANNEL)
    public PublishSubscribeChannel domainEventChannel(
            @Qualifier("messageTaskExecutor") ThreadPoolTaskExecutor messageTaskExecutor
    ) {
        return new PublishSubscribeChannel(messageTaskExecutor);
    }

    @Bean
    public EventDrivenConsumer domainEventHandler(
            PublishSubscribeChannel domainEventChannel,
            MessageHandler domainEventSender
    ) {
        return new EventDrivenConsumer(domainEventChannel, domainEventSender);
    }

    @Bean
    public MessageHandler domainEventSender(
            ApplicationContext applicationContext,
            DomainEventClassResolver domainEventClassResolver
    ) {
        return message -> {
            String domainEventClassName = message.getPayload().getClass().getSimpleName();
            if (domainEventClassResolver.contains(domainEventClassName)) {
                throw new NoSuchElementException();
            }
            MessageChannel messageChannel = applicationContext.getBean(domainEventClassName, MessageChannel.class);
            messageChannel.send(message);
        };
    }
}
