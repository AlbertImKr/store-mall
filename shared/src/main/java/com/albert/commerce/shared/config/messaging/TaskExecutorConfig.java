package com.albert.commerce.shared.config.messaging;

import java.util.concurrent.ThreadPoolExecutor.CallerRunsPolicy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class TaskExecutorConfig {

    @Value("${commerce.messaging.task-executor.core-pool-size}")
    private int corePoolSize;

    @Value("${commerce.messaging.task-executor.max-pool-size}")
    private int maxPoolSize;

    @Value("${commerce.messaging.task-executor.queue-capacity}")
    private int queueCapacity;

    @Value("${commerce.messaging.task-executor.thread-name-prefix}")
    private String threadNamePrefix;

    @Value("${commerce.messaging.task-executor.await-termination-seconds}")
    private int awaitTerminationSeconds;

    @Bean
    public ThreadPoolTaskExecutor messageTaskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
        executor.setRejectedExecutionHandler(new CallerRunsPolicy());
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(awaitTerminationSeconds);
        executor.initialize();
        return executor;
    }

}
