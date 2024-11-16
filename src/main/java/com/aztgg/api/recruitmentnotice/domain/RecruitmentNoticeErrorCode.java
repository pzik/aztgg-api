package com.aztgg.api.recruitmentnotice.domain;

import com.aztgg.api.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RecruitmentNoticeErrorCode implements BaseErrorCode {

    BAD_REQUEST_RECRUITMENT_NOTICE_NOT_FOUND(HttpStatus.BAD_REQUEST, "R_001", "id를 찾을 수 없음"),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
}
