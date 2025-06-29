package com.aztgg.api.auth.infrastructure.redis;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RedisRefreshTokenRepository extends CrudRepository<RedisRefreshToken, String> {
    void deleteByToken(String refreshToken);
}
