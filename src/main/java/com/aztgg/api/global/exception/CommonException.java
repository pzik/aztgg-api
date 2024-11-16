package com.aztgg.api.global.exception;

import lombok.Getter;
import org.springframework.web.server.ResponseStatusException;

@Getter
public class CommonException extends ResponseStatusException {

    private final BaseErrorCode errorCode;

    public CommonException(BaseErrorCode errorCode) {
        super(errorCode.getHttpStatus(), errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public CommonException(BaseErrorCode errorCode, String message) {
        super(errorCode.getHttpStatus(), message);
        this.errorCode = errorCode;
    }
}