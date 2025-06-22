package com.aztgg.api.auth.infrastructure;

import com.aztgg.api.auth.application.UserService;
import com.aztgg.api.auth.domain.User;
import com.aztgg.api.auth.domain.exception.AuthException;
import com.aztgg.api.auth.infrastructure.redis.RedisRefreshToken;
import com.aztgg.api.auth.infrastructure.redis.RedisRefreshTokenRepository;
import com.aztgg.api.global.security.JwtService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RefreshTokenService {

    private final RedisRefreshTokenRepository refreshTokenRepository;
    private final long refreshTokenExpirationMillis;
    private final JwtService jwtService;
    private final UserService userService;

    public RefreshTokenService(
            RedisRefreshTokenRepository refreshTokenRepository,
            @Value("${jwt.refresh-token.expiration}") long refreshTokenExpirationMillis,
            JwtService jwtService,
            UserService userService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.refreshTokenExpirationMillis = refreshTokenExpirationMillis;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public String createRefreshToken(String username) {
        refreshTokenRepository.deleteByUsername(username);

        User user = userService.findByUsername(username);

        String jwtToken = jwtService.generateRefreshToken(user);

        RedisRefreshToken refreshToken = RedisRefreshToken.createWithToken(username, jwtToken, refreshTokenExpirationMillis);
        refreshTokenRepository.save(refreshToken);

        return jwtToken;
    }

    public String validateRefreshToken(String token) {
        RedisRefreshToken refreshToken = refreshTokenRepository.findById(token)
                .orElseThrow(AuthException::invalidToken);

        return refreshToken.getUsername();
    }

    public String rotateRefreshToken(String oldToken, String username) {
        validateRefreshToken(oldToken);

        refreshTokenRepository.deleteById(oldToken);

        return createRefreshToken(username);
    }

    public void deleteRefreshToken(String token) {
        refreshTokenRepository.deleteById(token);
    }
}
