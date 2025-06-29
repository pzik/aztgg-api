package com.aztgg.api.auth.infrastructure.redis;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "refreshToken", timeToLive = 1209600)
@Getter
public class RedisRefreshToken {

    @Id
    private String token;
    private String username;
    private long expirationMillis;

    protected RedisRefreshToken() {}

    private RedisRefreshToken(String token, String username, long expirationMillis) {
        this.token = token;
        this.username = username;
        this.expirationMillis = expirationMillis;
    }

    public static RedisRefreshToken createWithToken(String username, String token, long expirationMillis) {
        return new RedisRefreshToken(token, username, expirationMillis / 1000); // Redis TTL 단위: 초
    }
}
