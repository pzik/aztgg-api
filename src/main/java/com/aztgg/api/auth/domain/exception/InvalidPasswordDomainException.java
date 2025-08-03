package com.aztgg.api.auth.domain.exception;

public class InvalidPasswordDomainException extends IllegalArgumentException {
    public InvalidPasswordDomainException() {
        super("유효하지 않은 비밀번호입니다.");
    }
}