package com.aztgg.api.auth.domain.exception;

import com.aztgg.api.global.exception.CommonException;

public class AuthException extends CommonException {

    public AuthException(AuthErrorCode errorCode) {
        super(errorCode);
    }

    public AuthException(AuthErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public static AuthException invalidCredentials() {
        return new AuthException(AuthErrorCode.INVALID_CREDENTIALS);
    }

    public static AuthException invalidToken() {
        return new AuthException(AuthErrorCode.INVALID_TOKEN);
    }

    public static AuthException userNotFound(String username) {
        return new AuthException(AuthErrorCode.USER_NOT_FOUND, "User not found with username: " + username);
    }
    public static AuthException userNotFound(Long userId) {
        return new AuthException(AuthErrorCode.USER_NOT_FOUND, "User not found with id: " + userId);
    }
    public static AuthException usernameAlreadyExists() {
        return new AuthException(AuthErrorCode.USERNAME_ALREADY_EXISTS);
    }
    public static AuthException emailAlreadyExists() {
        return new AuthException(AuthErrorCode.EMAIL_ALREADY_EXISTS);
    }

    public static AuthException nicknameAlreadyExists() {
        return new AuthException(AuthErrorCode.NICKNAME_ALREADY_EXISTS);
    }
}
