package com.aztgg.api.auth.application;

import com.aztgg.api.auth.application.dto.response.LoginResponse;
import com.aztgg.api.auth.application.strategy.AuthCredentials;
import com.aztgg.api.auth.application.strategy.AuthStrategy;
import com.aztgg.api.auth.domain.RefreshTokenManager;
import com.aztgg.api.auth.domain.User;
import com.aztgg.api.auth.domain.exception.AuthException;
import com.aztgg.api.auth.infrastructure.CookieUtil;
import com.aztgg.api.global.security.JwtService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class AuthService {

    private final JwtService jwtService;
    private final RefreshTokenManager refreshTokenManager;
    private final CookieUtil cookieUtil;
    private final UserService userService;
    private final Map<Class<? extends AuthCredentials>, AuthStrategy> strategyMap;

    public AuthService(List<AuthStrategy> authStrategies, JwtService jwtService,
        RefreshTokenManager refreshTokenManager, CookieUtil cookieUtil,
        UserService userService) {
        this.jwtService = jwtService;
        this.refreshTokenManager = refreshTokenManager;
        this.cookieUtil = cookieUtil;
        this.userService = userService;

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
        setRefreshTokenCookie(refreshToken);
        return new LoginResponse(accessToken);
    }

    public LoginResponse refresh(String refreshToken) {
        String username = refreshTokenManager.validateRefreshToken(refreshToken);
        User user = userService.findByUsername(username);

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        refreshTokenManager.rotateRefreshToken(refreshToken, username, newRefreshToken);
        setRefreshTokenCookie(newRefreshToken);
        return new LoginResponse(newAccessToken);
    }

    public void logout(String refreshToken) {
        refreshTokenManager.deleteRefreshToken(refreshToken);
        HttpServletResponse response = getCurrentResponse();
        cookieUtil.removeRefreshTokenCookie(response);
    }

    private void setRefreshTokenCookie(String refreshToken) {
        HttpServletResponse response = getCurrentResponse();
        cookieUtil.setRefreshTokenCookie(response, refreshToken);
    }

    private HttpServletResponse getCurrentResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }
}
