package com.aztgg.api.auth.application.exception;

import com.aztgg.api.global.exception.BaseErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter @RequiredArgsConstructor
public enum UserErrorCode implements BaseErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid credentials"),
    INSUFFICIENT_PERMISSIONS(HttpStatus.FORBIDDEN, "Insufficient permissions"),
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "Username already exists"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "Email already exists"),
    NICKNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "Nickname already exists"),
    KAKAO_AUTHENTICATION_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Error during Kakao authentication"),
    UNSUPPORTED_CREDENTIALS_TYPE(HttpStatus.BAD_REQUEST, "Unsupported credentials type");

    private final HttpStatus status;
    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return this.status;
    }

    @Override
    public String getCode() {
        return this.name();
    }
}
