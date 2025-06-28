package com.aztgg.api.auth.application.strategy.impl;

import com.aztgg.api.auth.application.UserService;
import com.aztgg.api.auth.application.strategy.AuthCredentials;
import com.aztgg.api.auth.application.strategy.AuthStrategy;
import com.aztgg.api.auth.application.strategy.AuthType;
import com.aztgg.api.auth.domain.Role;
import com.aztgg.api.auth.domain.User;
import com.aztgg.api.auth.domain.exception.AuthErrorCode;
import com.aztgg.api.auth.domain.exception.AuthException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminAuthStrategy implements AuthStrategy {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AdminAuthStrategy(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthType getType() {
        return AuthType.ADMIN;
    }

    @Override
    public User authenticate(AuthCredentials credentials) {
        AdminAuthCredentials adminCredentials = validateCredentials(credentials);

        String username = adminCredentials.getUsername();
        String password = adminCredentials.getPassword();

        User user = userService.findByUsername(username);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthException(AuthErrorCode.INVALID_CREDENTIALS, "Invalid password");
        }

        if (user.getRole() != Role.ADMIN) {
            throw new AuthException(AuthErrorCode.INSUFFICIENT_PERMISSIONS, "User is not an admin");
        }

        return user;
    }

    private AdminAuthCredentials validateCredentials(AuthCredentials credentials) {
        if (!(credentials instanceof AdminAuthCredentials adminCredentials)) {
            throw AuthException.invalidCredentials();
        }
        return adminCredentials;
    }

    @Override
    public Class<? extends AuthCredentials> getCredentialsType() {
        return AdminAuthCredentials.class;
    }
}
