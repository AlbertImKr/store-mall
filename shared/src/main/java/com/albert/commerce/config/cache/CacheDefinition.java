package com.albert.commerce.config.cache;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public record CacheDefinition(String cacheName, Duration ttl) {

    public static List<CacheDefinition> getCacheDefinitions() {
        return Arrays.stream(CacheConfigurations.values())
                .map(cacheConfiguration ->
                        new CacheDefinition(cacheConfiguration.getCacheName(), cacheConfiguration.getTtl()))
                .toList();
    }
}
