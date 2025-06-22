package com.aztgg.api.auth.application.strategy.impl;

import com.aztgg.api.auth.application.strategy.AuthCredentials;

public class KakaoAuthCredentials extends AuthCredentials {
    private KakaoAuthCredentials(String accessToken, String dummy) {
        super(accessToken, dummy);
    }

    public static KakaoAuthCredentials of(String accessToken) {
        return new KakaoAuthCredentials(accessToken, "");
    }

    public String getAccessToken() {
        return getPrincipal();
    }
}