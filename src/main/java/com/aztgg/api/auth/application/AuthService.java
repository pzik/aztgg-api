package com.aztgg.api.auth.application;

import com.aztgg.api.auth.application.dto.response.LoginResponse;
import com.aztgg.api.auth.application.exception.UserException;
import com.aztgg.api.auth.application.strategy.AuthCredentials;
import com.aztgg.api.auth.application.strategy.AuthStrategy;
import com.aztgg.api.auth.domain.JwtTokenDomainService;
import com.aztgg.api.auth.domain.RefreshTokenRepository;
import com.aztgg.api.auth.domain.User;
import com.aztgg.api.auth.domain.UserDomainService;
import com.aztgg.api.global.util.CookieUtil;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
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

    private final UserDomainService userDomainService;
    private final JwtTokenDomainService jwtTokenDomainService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final CookieUtil cookieUtil;
    private final Map<Class<? extends AuthCredentials>, AuthStrategy> strategyMap;

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    private final String refreshTokenCookieName = "refreshToken";

    public AuthService(
        UserDomainService userDomainService,
        List<AuthStrategy> authStrategies,
        JwtTokenDomainService jwtTokenDomainService,
        RefreshTokenRepository refreshTokenRepository,
        CookieUtil cookieUtil
    ) {
        this.userDomainService = userDomainService;
        this.jwtTokenDomainService = jwtTokenDomainService;
        this.refreshTokenRepository = refreshTokenRepository;
        this.cookieUtil = cookieUtil;

        this.strategyMap = authStrategies.stream()
            .collect(Collectors.toMap(
                AuthStrategy::getCredentialsType,
                Function.identity()
            ));
    }

    public LoginResponse authenticate(AuthCredentials credentials) {
        AuthStrategy strategy = strategyMap.get(credentials.getClass());
        if (strategy == null) {
            throw UserException.unsupportedCredentialsType(credentials.getClass().getName());
        }

        User user = strategy.authenticate(credentials);
        String accessToken = jwtTokenDomainService.generateAccessToken(user);
        String refreshToken = jwtTokenDomainService.generateRefreshToken(user);

        refreshTokenRepository.save(user.getUsername(), refreshToken);
        setRefreshTokenCookie(refreshToken);

        return new LoginResponse(accessToken);
    }

    public LoginResponse refresh(String refreshToken) {
        String username = refreshTokenRepository.validateRefreshToken(refreshToken);
        User user = userDomainService.findUserByUsername(username);

        String newAccessToken = jwtTokenDomainService.generateAccessToken(user);
        String newRefreshToken = jwtTokenDomainService.generateRefreshToken(user);

        refreshTokenRepository.rotateRefreshToken(refreshToken, username, newRefreshToken);
        setRefreshTokenCookie(newRefreshToken);

        return new LoginResponse(newAccessToken);
    }

    public void logout(String refreshToken) {
        refreshTokenRepository.deleteRefreshToken(refreshToken);
        removeRefreshTokenCookie();
    }

    private void setRefreshTokenCookie(String refreshToken) {
        HttpServletResponse response = getCurrentResponse();
        cookieUtil.setCookie(
            response,
            refreshTokenCookieName,
            refreshToken,
            (int) (refreshTokenExpiration / 1000),
            true,
            true
        );
    }

    private void removeRefreshTokenCookie() {
        HttpServletResponse response = getCurrentResponse();
        cookieUtil.removeCookie(response, refreshTokenCookieName);
    }

    private HttpServletResponse getCurrentResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }
}
