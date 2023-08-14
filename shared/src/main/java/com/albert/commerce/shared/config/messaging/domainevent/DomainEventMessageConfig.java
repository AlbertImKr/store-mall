package com.albert.commerce.shared.config.messaging.domainevent;

import com.albert.commerce.shared.messaging.domain.event.DomainEvent;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Configuration
public class DomainEventMessageConfig {

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
                .filter(method -> method.getDeclaringClass().getPackage().getName()
                        .matches(domainEventListenerBasePackage))
                .filter(method -> method.getParameterTypes().length != 0)
                .filter(method -> DomainEvent.class.isAssignableFrom(method.getParameterTypes()[0]))
                .map(method -> method.getParameterTypes()[0].getName())
                .collect(Collectors.toSet());
    }

    @Bean
    public DomainEventClassResolver domainEventClassResolver() {
        Reflections reflections = new Reflections(apiBasePackage);
        Set<Class<? extends DomainEvent>> domainEventClasses = reflections.getSubTypesOf(DomainEvent.class);
        return new DomainEventClassResolver(domainEventClasses);
    }

    @Bean
    public PublishSubscribeChannel domainEventChannel(ThreadPoolTaskExecutor messageTaskExecutor) {
        return new PublishSubscribeChannel(messageTaskExecutor);
    }

    @Bean
    public EventDrivenConsumer domainEventHandler(PublishSubscribeChannel domainEventChannel,
            MessageHandler domainEventSender) {
        EventDrivenConsumer consumer = new EventDrivenConsumer(domainEventChannel, domainEventSender);
        consumer.start();
        return consumer;
    }

    @Bean
    public MessageHandler domainEventSender(Set<String> domainEventClassNames) {
        return message -> {
            String domainEventClassName = message.getPayload().getClass().getName();
            if (domainEventClassNames.contains(domainEventClassName)) {
                Optional<PublishSubscribeChannel> channel = DomainEventChannelRegistry.findChannel(
                        domainEventClassName);
                if (channel.isPresent()) {
                    channel.get().send(message);
                } else {
                    throw new IllegalArgumentException(
                            "No channel found for domain event class: " + domainEventClassName);
                }
            } else {
                throw new IllegalArgumentException("Unsupported domain event class: " + domainEventClassName);
            }
        };
    }


    @Component
    private static class DomainEventChannelRegistry {

        private static final Map<String, PublishSubscribeChannel> channels = new ConcurrentHashMap<>();

        public DomainEventChannelRegistry(Set<String> domainEventClassNames, Executor messageTaskExecutor,
                ConfigurableListableBeanFactory beanFactory) {
            for (String className : domainEventClassNames) {
                PublishSubscribeChannel channel = new PublishSubscribeChannel(messageTaskExecutor, true);
                channel.setBeanName(className);
                beanFactory.registerSingleton(className, channel);
                this.registerChannel(className, channel);
            }
        }

        public static Optional<PublishSubscribeChannel> findChannel(String className) {
            return Optional.ofNullable(channels.get(className));
        }

        public void registerChannel(String className, PublishSubscribeChannel channel) {
            channels.put(className, channel);
        }
    }

}
