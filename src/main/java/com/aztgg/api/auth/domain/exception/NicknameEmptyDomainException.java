package com.aztgg.api.auth.domain.exception;

public class NicknameEmptyDomainException extends IllegalArgumentException {
    public NicknameEmptyDomainException() {
        super("닉네임은 비어 있을 수 없습니다.");
    }
}