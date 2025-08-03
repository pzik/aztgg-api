package com.aztgg.api.auth.application.exception;

import com.aztgg.api.global.exception.CommonException;

public class UserException extends CommonException {

    public UserException(UserErrorCode errorCode) {
        super(errorCode);
    }

    public UserException(UserErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public static UserException invalidCredentials() {
        return new UserException(UserErrorCode.INVALID_CREDENTIALS);
    }

    public static UserException unsupportedCredentialsType(String credentialsType) {
        return new UserException(UserErrorCode.UNSUPPORTED_CREDENTIALS_TYPE, "Unsupported credentials type: " + credentialsType);
    }
}
