package com.albert.commerce.config.cache;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

@Profile(value = "cache")
@Configuration
@EnableCaching
public class RedisCacheConfig {

    @Bean
    public CacheConfigurationBuilder cacheConfigurationBuilder(RedisConnectionFactory redisConnectionFactory) {
        return new CacheConfigurationBuilder(redisConnectionFactory);
    }

    @Bean
    public CacheManager cacheManager(CacheConfigurationBuilder cacheConfigurationBuilder) {
        return cacheConfigurationBuilder.buildCacheManager(CacheDefinition.getCacheDefinitions());
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
