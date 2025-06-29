package com.aztgg.api.auth.application.strategy;

public abstract class AuthCredentials {
    private final String principal;
    private final String credential;

    protected AuthCredentials(String principal, String credential) {
        this.principal = principal;
        this.credential = credential;
    }

    public String getPrincipal() {
        return principal;
    }

    public String getCredential() {
        return credential;
    }
} 