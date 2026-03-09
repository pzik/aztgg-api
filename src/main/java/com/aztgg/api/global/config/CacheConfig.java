package com.aztgg.api.global.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

@EnableCaching
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .disableCachingNullValues() // null value 는 캐시에 저장하지 않음
                .entryTtl(Duration.ofMinutes(30)) // 기본 캐시 지속시간
                .prefixCacheNameWith("aztgg:cache") // key prefix
                .serializeValuesWith(RedisSerializationContext
                        .SerializationPair
                        .fromSerializer(new GenericJackson2JsonRedisSerializer())); // 이 시리얼라이저는 자바앱끼리만 가능함

        return RedisCacheManager.RedisCacheManagerBuilder
                .fromConnectionFactory(redisConnectionFactory)
                .cacheDefaults(configuration)
                .build();
    }
}
