package com.aztgg.api.global.security;

import lombok.Getter;

@Getter
public class CustomUserPrincipal {
    private final String userId;
    private final String email;

    public CustomUserPrincipal(String userId, String email) {
        this.userId = userId;
        this.email = email;
    }

	@Override
    public String toString() {
        return userId;
    }
}
