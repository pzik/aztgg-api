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

    // User validation exceptions
    public static AuthException invalidUsername() {
        return new AuthException(AuthErrorCode.INVALID_USERNAME);
    }

    public static AuthException invalidPassword() {
        return new AuthException(AuthErrorCode.INVALID_PASSWORD);
    }

    public static AuthException invalidEmail() {
        return new AuthException(AuthErrorCode.INVALID_EMAIL);
    }

    public static AuthException invalidNickname() {
        return new AuthException(AuthErrorCode.INVALID_NICKNAME);
    }

    public static AuthException invalidRole() {
        return new AuthException(AuthErrorCode.INVALID_ROLE);
    }

    public static AuthException nicknameTooLong() {
        return new AuthException(AuthErrorCode.NICKNAME_TOO_LONG);
    }

    public static AuthException nicknameEmpty() {
        return new AuthException(AuthErrorCode.NICKNAME_EMPTY);
    }
}
