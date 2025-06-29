package com.aztgg.api.auth.infrastructure;

import com.aztgg.api.auth.domain.RefreshTokenRepository;
import com.aztgg.api.auth.domain.exception.AuthException;
import com.aztgg.api.auth.infrastructure.redis.RedisRefreshToken;
import com.aztgg.api.auth.infrastructure.redis.RedisRefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RefreshTokenRepositoryImpl implements RefreshTokenRepository {

    private final RedisRefreshTokenRepository refreshTokenRepository;
    private final long refreshTokenExpirationMillis;

    public RefreshTokenRepositoryImpl(
        RedisRefreshTokenRepository refreshTokenRepository,
        @Value("${jwt.refresh-token.expiration}") long refreshTokenExpirationMillis
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenExpirationMillis = refreshTokenExpirationMillis;
    }

    public void save(String username, String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
        RedisRefreshToken token = RedisRefreshToken.createWithToken(username, refreshToken, refreshTokenExpirationMillis);
        refreshTokenRepository.save(token);
    }

    public String validateRefreshToken(String token) {
        return refreshTokenRepository.findById(token)
            .orElseThrow(AuthException::invalidToken)
            .getUsername();
    }

    public void rotateRefreshToken(String oldToken, String username, String newToken) {
        validateRefreshToken(oldToken);
        refreshTokenRepository.deleteById(oldToken);
        save(username, newToken);
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteById(token);
    }
}
