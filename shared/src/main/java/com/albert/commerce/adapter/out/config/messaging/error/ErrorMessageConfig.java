package com.albert.commerce.adapter.out.config.messaging.error;

import static com.albert.commerce.domain.units.DomainEventChannelNames.ERROR_CHANNEL;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.messaging.MessageChannel;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Profile("messaging")
@RequiredArgsConstructor
@Configuration
public class ErrorMessageConfig {


    @Bean(ERROR_CHANNEL)
    public PublishSubscribeChannel errorChannel(
            @Qualifier("messageTaskExecutor") ThreadPoolTaskExecutor messageTaskExecutor
    ) {
        return new PublishSubscribeChannel(messageTaskExecutor);
    }

    @Bean
    public IntegrationFlow errorHandlingFlow(
            @Qualifier(ERROR_CHANNEL) MessageChannel errorChannel,
            ErrorHandler errorHandler
    ) {
        return IntegrationFlow.from(errorChannel)
                .handle(errorHandler, "handleErrorMessage")
                .get();
    }
}
