package com.albert.commerce.config.cache;

import java.time.Duration;

public enum CacheConfigurations {

    USER(CacheValue.USER, Duration.ofHours(1)),
    STORE(CacheValue.STORE, Duration.ofHours(1)),
    PRODUCT(CacheValue.PRODUCT, Duration.ofHours(1)),
    ORDER(CacheValue.ORDER, Duration.ofHours(10)),
    COMMENT(CacheValue.COMMENT, Duration.ofHours(1));


    private final String cacheName;
    private final Duration ttl;

    CacheConfigurations(String cacheName, Duration ttl) {
        this.cacheName = cacheName;
        this.ttl = ttl;
    }

    public String getCacheName() {
        return cacheName;
    }

    public Duration getTtl() {
        return ttl;
    }
}
