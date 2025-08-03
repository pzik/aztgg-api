package com.aztgg.api.auth.domain.exception;

public class InvalidNicknameDomainException extends IllegalArgumentException {
    public InvalidNicknameDomainException() {
        super("유효하지 않은 닉네임입니다.");
    }
}