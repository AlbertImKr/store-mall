package com.albert.commerce.shared.config.messaging.domainevent;

import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.support.GenericWebApplicationContext;

@Configuration
public class DomainEventMessageConfig {

    public static final String DOMAIN_EVENT_CHANNEL = "domainEventChannel";

    @Value("${commerce.messaging.base-package.domain-event-listener}")
    private String domainEventListenerBasePackage;
    @Value("${commerce.messaging.base-package.domain-event}")
    private String domainEventBasePackage;
    @Value("${commerce.messaging.base-package.api}")
    private String apiBasePackage;

    @Bean
    public Set<String> domainEventClassNames() {
        Reflections reflections = new Reflections(
                new ConfigurationBuilder()
                        .setUrls(ClasspathHelper.forPackage(apiBasePackage))
                        .setScanners(new MethodAnnotationsScanner()));
        Set<Method> methods = reflections.getMethodsAnnotatedWith(ServiceActivator.class);

        return methods.stream()
                .map(method -> method.getAnnotation(ServiceActivator.class).inputChannel())
                .collect(Collectors.toSet());
    }

    @Bean
    public DomainEventClassResolver domainEventClassResolver() {
        Reflections reflections = new Reflections(apiBasePackage);
        Set<Class<? extends DomainEvent>> domainEventClasses = reflections.getSubTypesOf(DomainEvent.class);
        return new DomainEventClassResolver(domainEventClasses);
    }

    @Bean
    public boolean registerChannel(GenericWebApplicationContext context, TaskExecutor messageTaskExecutor,
            Set<String> domainEventClassNames) {
        for (String className : domainEventClassNames) {
            context.registerBean(className, PublishSubscribeChannel.class, messageTaskExecutor, true);
        }
        return true;
    }

    @Bean(DOMAIN_EVENT_CHANNEL)
    public PublishSubscribeChannel domainEventChannel(ThreadPoolTaskExecutor messageTaskExecutor) {
        return new PublishSubscribeChannel(messageTaskExecutor);
    }

    @Bean
    public EventDrivenConsumer domainEventHandler(PublishSubscribeChannel domainEventChannel,
            MessageHandler domainEventSender) {
        return new EventDrivenConsumer(domainEventChannel, domainEventSender);
    }

    @Bean
    public MessageHandler domainEventSender(ApplicationContext applicationContext) {
        return message -> {
            String domainEventClassName = message.getPayload().getClass().getSimpleName();
            MessageChannel messageChannel = applicationContext.getBean(domainEventClassName, MessageChannel.class);
            messageChannel.send(message);
        };
    }
}
