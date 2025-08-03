package com.aztgg.api.auth.domain.exception;

public class InvalidTokenDomainException extends IllegalArgumentException {
    public InvalidTokenDomainException() {
        super("유효하지 않은 토큰입니다.");
    }
}
