package com.aztgg.api.board.application.exception;

import com.aztgg.api.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum BoardErrorCode implements BaseErrorCode {

    INVALID_USER(HttpStatus.UNAUTHORIZED, "BOARD_001", "invalid user"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
