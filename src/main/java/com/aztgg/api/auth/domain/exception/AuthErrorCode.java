package com.aztgg.api.auth.domain.exception;

import com.aztgg.api.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

    /* 401 UNAUTHORIZED */
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH_001", "Invalid credentials"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "AUTH_002", "Invalid password"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_003", "Invalid token"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "AUTH_004", "Expired token"),
    TOKEN_VALIDATION_FAILED(HttpStatus.UNAUTHORIZED, "AUTH_005", "Token validation failed"),

    /* 403  FORBIDDEN */
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "AUTH_201", "Access denied"),
    INSUFFICIENT_PERMISSIONS(HttpStatus.FORBIDDEN, "AUTH_202", "Insufficient permissions"),


    /* 404 NOT FOUND */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "AUTH_101", "User not found"),

    /* 409 CONFLICT */
    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "AUTH_102", "Username already exists"),
    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "AUTH_103", "Email already exists"),
    NICKNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "AUTH_104", "Nickname already exists");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
