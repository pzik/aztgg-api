package com.aztgg.api.global.util;

import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieUtil {


    public void setCookie(HttpServletResponse response, String name, String value, int maxAge, boolean httpOnly, boolean secure) {
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
        response.setHeader("Set-Cookie", name + "=; Max-Age=0; Path=/; HttpOnly; Secure; SameSite=Strict");
    }
} 
