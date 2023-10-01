package com.albert.commerce.adapter.out.config.cache;

import static com.albert.commerce.adapter.out.config.cache.CacheDefinition.getCacheConfigurations;

import java.util.List;
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
        var defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig();
        var cacheConfigurations = getCacheConfigurations(cacheDefinitions, defaultCacheConfig);
        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(defaultCacheConfig)
                .withInitialCacheConfigurations(cacheConfigurations)
                .build();
    }
}
