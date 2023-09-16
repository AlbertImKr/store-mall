package com.albert.commerce.config.cache;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

public class CacheConfigurationBuilder {

    private final RedisConnectionFactory redisConnectionFactory;

    public CacheConfigurationBuilder(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }

    public CacheManager buildCacheManager(List<CacheDefinition> cacheDefinitions) {
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();

        Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

        for (CacheDefinition definition : cacheDefinitions) {
            cacheConfigurations.put(definition.cacheName(), defaultCacheConfig.entryTtl(definition.ttl()));
        }

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
}
