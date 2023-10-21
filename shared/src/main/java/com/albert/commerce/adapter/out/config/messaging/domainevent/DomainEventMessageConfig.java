package com.albert.commerce.adapter.out.config.messaging.domainevent;

import static com.albert.commerce.domain.units.DomainEventChannelNames.DOMAIN_EVENT_CHANNEL;
import static com.albert.commerce.domain.units.DomainEventChannelNames.ERROR_CHANNEL;

import com.albert.commerce.domain.event.DomainEvent;
import com.albert.commerce.domain.event.DomainEventDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.NoSuchElementException;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.endpoint.EventDrivenConsumer;
import org.springframework.integration.kafka.dsl.Kafka;
import org.springframework.integration.kafka.dsl.KafkaMessageDrivenChannelAdapterSpec.KafkaMessageDrivenChannelAdapterListenerContainerSpec;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Profile("messaging")
@RequiredArgsConstructor
@Configuration
public class DomainEventMessageConfig {

    private final ObjectMapper objectMapper;
    @Value("${commerce.messaging.base-package.domain-event}")
    private String domainEventBasePackage;
    @Value("${commerce.messaging.base-package.domain-event-dto}")
    private String domainEventDTOPackage;

    @Bean
    public DomainEventClassResolver domainEventClassResolver() {
        Reflections reflections = new Reflections(domainEventBasePackage);
        Set<Class<? extends DomainEvent>> classes = reflections.getSubTypesOf(DomainEvent.class);
        return new DomainEventClassResolver(classes);
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

    @Bean
    public DomainEventDTOResolver domainEventDTOResolver() {
        Reflections reflections = new Reflections(domainEventDTOPackage);
        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(DomainEventDTO.class);
        return new DomainEventDTOResolver(classes);
    }

    @Bean
    public boolean registerFlow(
            DomainEventDTOResolver domainEventDTOResolver,
            IntegrationFlowContext flowContext,
            ConsumerFactory<Object, Object> consumerFactory
    ) {
        for (String channelName : domainEventDTOResolver.getChannelNames()) {
            Class<?> payloadType = domainEventDTOResolver.get(channelName);
            var messageProducerSpec = getChannelAdapterListenerContainerSpec(
                    channelName,
                    consumerFactory,
                    payloadType
            );
            IntegrationFlow flow = createFlowForChannel(channelName, messageProducerSpec);
            flowContext.registration(flow).register();
        }
        return true;
    }

    private IntegrationFlow createFlowForChannel(
            String channelName,
            KafkaMessageDrivenChannelAdapterListenerContainerSpec<Object, Object> producerSpec
    ) {
        return IntegrationFlow
                .from(producerSpec)
                .channel(channelName)
                .get();
    }

    private KafkaMessageDrivenChannelAdapterListenerContainerSpec<Object, Object> getChannelAdapterListenerContainerSpec(
            String channelName,
            ConsumerFactory<Object, Object> consumerFactory,
            Class<?> payloadType) {
        var messageDrivenChannelAdapter = Kafka.messageDrivenChannelAdapter(consumerFactory, channelName);
        StringJsonMessageConverter messageConverter = new StringJsonMessageConverter(objectMapper);
        messageDrivenChannelAdapter.messageConverter(messageConverter);
        messageDrivenChannelAdapter.payloadType(payloadType);
        messageDrivenChannelAdapter.errorChannel(ERROR_CHANNEL);
        return messageDrivenChannelAdapter;
    }
}
