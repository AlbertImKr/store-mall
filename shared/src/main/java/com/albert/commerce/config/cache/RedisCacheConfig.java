package com.albert.commerce.config.cache;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Profile(value = "cache")
@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Value("${commerce.messaging.base-package.cache-config}")
    private String cacheConfigBasePackage;

    @Bean
    public List<CacheConfig> cacheConfigs(ApplicationContext context) {
        Reflections reflections = new Reflections(cacheConfigBasePackage);
        Set<Class<? extends CacheConfig>> cacheConfigClasses = reflections.getSubTypesOf(CacheConfig.class);
        List<CacheConfig> cacheConfigs = new ArrayList<>();
        for (Class<? extends CacheConfig> cacheConfig : cacheConfigClasses) {
            String simpleName = cacheConfig.getSimpleName();
            if (context.containsBean(simpleName)) {
                cacheConfigs.add(context.getBean(simpleName, CacheConfig.class));
            }
        }
        return cacheConfigs;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory,
            List<CacheConfig> cacheConfigs) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        for (CacheConfig config : cacheConfigs) {
            cacheConfigurations.put(
                    config.getCacheName(),
                    defaultCacheConfig.entryTtl(Duration.ofSeconds(config.getTtl()))
            );
        }

        return RedisCacheManager
                .builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        template.setKeySerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }
}
