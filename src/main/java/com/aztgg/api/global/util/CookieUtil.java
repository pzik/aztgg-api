package com.aztgg.api.global.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CookieUtil {

    public void setCookie(HttpServletResponse response, String name, String value, int maxAge, boolean httpOnly, boolean secure) {
        validateCookieName(name);
        validateCookieValue(value);

        StringBuilder cookieBuilder = new StringBuilder();
        cookieBuilder.append(name).append("=").append(value);
        cookieBuilder.append("; Max-Age=").append(maxAge);
        cookieBuilder.append("; Path=/");

        if (httpOnly) {
            cookieBuilder.append("; HttpOnly");
        }

        if (secure) {
            cookieBuilder.append("; Secure");
        }

        cookieBuilder.append("; SameSite=Strict");

        response.setHeader("Set-Cookie", cookieBuilder.toString());
    }

    public void removeCookie(HttpServletResponse response, String name) {
        validateCookieName(name);
        response.setHeader("Set-Cookie", name + "=; Max-Age=0; Path=/; HttpOnly; Secure; SameSite=Strict");
    }

    private static void validateCookieName(String name) {
        if (!StringUtils.hasLength(name) || name.length() < 1) {
            throw new IllegalArgumentException("Cookie name must be at least 1 character");
        }
    }

    private static void validateCookieValue(String value) {
        if (!StringUtils.hasLength(value) || value.length() < 1) {
            throw new IllegalArgumentException("Cookie value must be at least 1 character");
        }
    }
}
