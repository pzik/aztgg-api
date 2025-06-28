package com.aztgg.api.auth.application.strategy.impl;

import com.aztgg.api.auth.application.strategy.AuthCredentials;

public class AdminAuthCredentials extends AuthCredentials {
    private AdminAuthCredentials(String username, String password) {
        super(username, password);
    }

    public static AdminAuthCredentials of(String username, String password) {
        return new AdminAuthCredentials(username, password);
    }

    public String getUsername() {
        return getPrincipal();
    }

    public String getPassword() {
        return getCredential();
    }
}
