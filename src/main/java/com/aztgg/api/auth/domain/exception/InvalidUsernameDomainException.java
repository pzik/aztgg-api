package com.aztgg.api.auth.domain.exception;

public class InvalidUsernameDomainException extends IllegalArgumentException {
    public InvalidUsernameDomainException() {
        super("유효하지 않은 이름입니다.");
    }
}