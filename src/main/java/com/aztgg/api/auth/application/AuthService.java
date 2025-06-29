package com.aztgg.api.auth.application;

import com.aztgg.api.auth.application.dto.response.LoginResponse;
import com.aztgg.api.auth.application.strategy.AuthCredentials;
import com.aztgg.api.auth.application.strategy.AuthStrategy;
import com.aztgg.api.auth.domain.RefreshTokenManager;
import com.aztgg.api.auth.domain.User;
import com.aztgg.api.auth.domain.UserDomainService;
import com.aztgg.api.auth.domain.exception.AuthException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthService {

    private final UserDomainService userDomainService;
    private final JwtService jwtService;
    private final RefreshTokenManager refreshTokenManager;
    private final RefreshTokenCookieService refreshTokenCookieService;
    private final Map<Class<? extends AuthCredentials>, AuthStrategy> strategyMap;

    public AuthService(UserDomainService userDomainService, List<AuthStrategy> authStrategies, JwtService jwtService,
        RefreshTokenManager refreshTokenManager, RefreshTokenCookieService refreshTokenCookieService
        ) {
		this.userDomainService = userDomainService;
		this.jwtService = jwtService;
        this.refreshTokenManager = refreshTokenManager;
        this.refreshTokenCookieService = refreshTokenCookieService;

        this.strategyMap = authStrategies.stream()
            .collect(Collectors.toMap(
                AuthStrategy::getCredentialsType,
                Function.identity()
            ));
    }

    public LoginResponse authenticate(AuthCredentials credentials) {
        AuthStrategy strategy = strategyMap.get(credentials.getClass());
        if (strategy == null) {
            throw AuthException.invalidCredentials();
        }

        User user = strategy.authenticate(credentials);
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        refreshTokenManager.save(user.getUsername(), refreshToken);
        refreshTokenCookieService.setRefreshTokenCookie(refreshToken);
        return new LoginResponse(accessToken);
    }

    public LoginResponse refresh(String refreshToken) {
        String username = refreshTokenManager.validateRefreshToken(refreshToken);
        User user = userDomainService.findUserByUsername(username);

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        refreshTokenManager.rotateRefreshToken(refreshToken, username, newRefreshToken);
        refreshTokenCookieService.setRefreshTokenCookie(refreshToken);
        return new LoginResponse(newAccessToken);
    }

    public void logout(String refreshToken) {
        refreshTokenManager.deleteRefreshToken(refreshToken);
        refreshTokenCookieService.removeRefreshTokenCookie();
    }
}
