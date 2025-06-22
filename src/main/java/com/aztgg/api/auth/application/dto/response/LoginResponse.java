package com.aztgg.api.auth.application.dto.response;

import lombok.Getter;

@Getter
public class LoginResponse {
    private final String accessToken;

    public LoginResponse(String accessToken) {
        this.accessToken = accessToken;
    }
} 
