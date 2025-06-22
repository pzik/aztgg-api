package com.aztgg.api.auth.application;

import com.aztgg.api.auth.application.dto.response.LoginResponse;
import com.aztgg.api.auth.application.strategy.AuthCredentials;
import com.aztgg.api.auth.application.strategy.AuthStrategy;
import com.aztgg.api.auth.domain.User;
import com.aztgg.api.auth.domain.exception.AuthException;
import com.aztgg.api.auth.infrastructure.CookieUtil;
import com.aztgg.api.global.security.JwtService;
import com.aztgg.api.auth.infrastructure.RefreshTokenService;
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
    private final RefreshTokenService refreshTokenService;
    private final CookieUtil cookieUtil;
    private final UserService userService;

    private final Map<Class<? extends AuthCredentials>, AuthStrategy> strategyMap;

    public AuthService(List<AuthStrategy> authStrategies, JwtService jwtService, 
                      RefreshTokenService refreshTokenService, CookieUtil cookieUtil,
                      UserService userService) {
        this.jwtService = jwtService;
        this.refreshTokenService = refreshTokenService;
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
        String refreshToken = refreshTokenService.createRefreshToken(user.getUsername());

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        cookieUtil.setRefreshTokenCookie(response, refreshToken);

        return new LoginResponse(accessToken);
    }

    public LoginResponse refresh(String refreshToken) {

        String username = refreshTokenService.validateRefreshToken(refreshToken);
        User user = userService.findByUsername(username);


        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = refreshTokenService.rotateRefreshToken(refreshToken, username);


        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        cookieUtil.setRefreshTokenCookie(response, newRefreshToken);

        return new LoginResponse(newAccessToken);
    }

    public void logout(String refreshToken) {
        refreshTokenService.deleteRefreshToken(refreshToken);

        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        cookieUtil.removeRefreshTokenCookie(response);
    }

}
