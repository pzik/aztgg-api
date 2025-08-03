package com.aztgg.api.auth.domain.exception;

public class InvalidRoleDomainException extends IllegalArgumentException {
    public InvalidRoleDomainException() {
        super("유효하지 않은 역할입니다.");
    }
}