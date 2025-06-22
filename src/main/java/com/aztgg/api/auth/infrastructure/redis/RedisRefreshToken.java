package com.aztgg.api.auth.infrastructure.redis;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Getter
@RedisHash("refreshToken")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RedisRefreshToken {

    @Id
    private String token;

    @Indexed
    private String username;

    private LocalDateTime createdAt;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private Long timeToLive;

    private RedisRefreshToken(String token, String username, Long timeToLiveInSeconds) {
        this.token = token;
        this.username = username;
        this.createdAt = LocalDateTime.now();
        this.timeToLive = timeToLiveInSeconds;
    }

    public static RedisRefreshToken createWithToken(String username, String token, long expirationInMillis) {
        long timeToLiveInSeconds = expirationInMillis / 1000;
        return new RedisRefreshToken(token, username, timeToLiveInSeconds);
    }
}
