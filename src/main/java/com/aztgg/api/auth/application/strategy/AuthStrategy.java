package com.aztgg.api.auth.application.strategy;

import com.aztgg.api.auth.domain.User;

public interface AuthStrategy {
    AuthType getType();
    User authenticate(AuthCredentials credentials);
    Class<? extends AuthCredentials> getCredentialsType();
} 