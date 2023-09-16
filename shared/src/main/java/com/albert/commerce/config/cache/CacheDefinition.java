package com.albert.commerce.config.cache;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.data.redis.cache.RedisCacheConfiguration;

public record CacheDefinition(String cacheName, Duration ttl) {

    public static List<CacheDefinition> getCacheDefinitions() {
        return Arrays.stream(CacheConfigurations.values())
                .map(cacheConfiguration ->
                        new CacheDefinition(cacheConfiguration.getCacheName(), cacheConfiguration.getTtl()))
                .toList();
    }

    public static Map<String, RedisCacheConfiguration> getCacheConfigurations(
            List<CacheDefinition> cacheDefinitions,
            RedisCacheConfiguration defaultCacheConfig
    ) {
        return cacheDefinitions.stream()
                .collect(Collectors.toMap(
                        CacheDefinition::cacheName,
                        cacheDefinition -> defaultCacheConfig.entryTtl(cacheDefinition.ttl())
                ));
    }
}
