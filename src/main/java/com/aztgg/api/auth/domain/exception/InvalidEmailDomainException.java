package com.aztgg.api.auth.domain.exception;

public class InvalidEmailDomainException extends IllegalArgumentException {
    public InvalidEmailDomainException() {
        super("유효하지 않은 이메일입니다.");
    }
}