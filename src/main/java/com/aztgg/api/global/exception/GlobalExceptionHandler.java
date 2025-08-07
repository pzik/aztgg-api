package com.aztgg.api.global.exception;

import com.aztgg.api.global.logging.AppLogger;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception ex) {
        CommonErrorCode commonErrorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;

        ErrorResponse error = ErrorResponse.builder()
                .status(commonErrorCode.getHttpStatus().value())
                .message(ex.getMessage())
                .code(commonErrorCode.getCode())
                .build();

        AppLogger.errorLog(ex.getMessage(), ex);
        return new ResponseEntity<>(error, commonErrorCode.getHttpStatus());
    }

    /**
     * Bean Validation에 실패했을 때, 에러메시지를 내보내기 위한 Exception Handler
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleParamViolationException(BindException ex) {
        // 파라미터 validation에 걸렸을 경우
        CommonErrorCode commonErrorCode = CommonErrorCode.REQUEST_PARAMETER_BIND_FAILED;

        ErrorResponse error = ErrorResponse.builder()
                .status(commonErrorCode.getHttpStatus().value())
                .message(ex.getMessage())
                .code(commonErrorCode.getCode())
                .build();

        AppLogger.errorLog(ex.getMessage(), ex);
        return new ResponseEntity<>(error, commonErrorCode.getHttpStatus());
    }

    @ExceptionHandler(CommonException.class)
    protected ResponseEntity<ErrorResponse> handleApplicationException(CommonException ex) {
        BaseErrorCode errorCode = ex.getErrorCode();

        ErrorResponse error = ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .message(ex.getMessage())
                .code(errorCode.getCode())
                .build();

        AppLogger.errorLog(ex.getMessage(), ex);
        return new ResponseEntity<>(error, errorCode.getHttpStatus());
    }

    // 핸들링되지 않는 도메인 익셉션은 5xx
    @ExceptionHandler(DomainException.class)
    protected ResponseEntity<ErrorResponse> handleDomainException(DomainException ex) {
        BaseErrorCode errorCode = CommonErrorCode.INTERNAL_SERVER_ERROR;

        ErrorResponse error = ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .message(ex.getMessage())
                .code(errorCode.getCode())
                .build();

        AppLogger.errorLog(ex.getMessage(), ex);
        return new ResponseEntity<>(error, errorCode.getHttpStatus());
    }
}