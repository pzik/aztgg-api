package com.aztgg.api.auth.domain.exception;

public class NicknameTooLongDomainException extends IllegalArgumentException {
    public NicknameTooLongDomainException() {
        super("닉네임은 20자를 초과할 수 없습니다.");
    }
}