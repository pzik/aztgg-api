package com.aztgg.api.auth.domain.exception;

import com.aztgg.api.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

    /* 400 BAD REQUEST */
    INVALID_USERNAME(HttpStatus.BAD_REQUEST, "AUTH_400", "Username must not be null or blank"),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "AUTH_401", "Password must be at least 20 characters long"),
    INVALID_EMAIL(HttpStatus.BAD_REQUEST, "AUTH_402", "Email is not valid"),
    INVALID_NICKNAME(HttpStatus.BAD_REQUEST, "AUTH_403", "Nickname must be at least 2 characters long"),
    INVALID_ROLE(HttpStatus.BAD_REQUEST, "AUTH_404", "Role must not be null"),
    NICKNAME_TOO_LONG(HttpStatus.BAD_REQUEST, "AUTH_405", "Nickname cannot be longer than 20 characters"),
    NICKNAME_EMPTY(HttpStatus.BAD_REQUEST, "AUTH_406", "Nickname cannot be empty"),

    /* 401 UNAUTHORIZED */  
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "AUTH_001", "Invalid credentials"),
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
