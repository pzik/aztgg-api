package com.aztgg.api.auth.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieUtil {

    @Value("${jwt.refresh-token.expiration}")
    private Long refreshTokenExpiration;

    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken) {
        response.setHeader("Set-Cookie", String.format("refreshToken=%s; Max-Age=%d; Path=/; HttpOnly; Secure; SameSite=Strict", 
                refreshToken, (int) (refreshTokenExpiration / 1000)));
    }

    public void removeRefreshTokenCookie(HttpServletResponse response) {
        response.setHeader("Set-Cookie", "refreshToken=; Max-Age=0; Path=/; HttpOnly; Secure; SameSite=Strict");
    }
} 
