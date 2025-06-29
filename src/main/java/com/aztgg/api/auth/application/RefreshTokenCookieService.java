package com.aztgg.api.auth.application;

import com.aztgg.api.global.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class RefreshTokenCookieService {

    private final CookieUtil cookieUtil;

    private final String refreshTokenCookieName = "refreshToken";
    
    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    public RefreshTokenCookieService(CookieUtil cookieUtil) {
        this.cookieUtil = cookieUtil;
    }

    public void setRefreshTokenCookie(String refreshToken) {
        HttpServletResponse response = getCurrentResponse();
        cookieUtil.setCookie(response, refreshTokenCookieName, refreshToken, (int) (refreshTokenExpiration / 1000), true, true);
    }

    public void removeRefreshTokenCookie() {
        HttpServletResponse response = getCurrentResponse();
        cookieUtil.removeCookie(response, refreshTokenCookieName);
    }

    private HttpServletResponse getCurrentResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    }
}
