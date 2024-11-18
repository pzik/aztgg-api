package com.aztgg.api.recruitmentnotice.application.dto;

import com.aztgg.api.recruitmentnotice.domain.RecruitmentNotice;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record GetRecruitmentNoticeRedirectionResponseDto(Long recruitmentNoticeId,
                                                         String jobOfferTitle,
                                                         String companyCode,
                                                         @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") LocalDateTime startAt,
                                                         @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") LocalDateTime endAt) {

    public static GetRecruitmentNoticeRedirectionResponseDto from(RecruitmentNotice recruitmentNotice) {
        return new GetRecruitmentNoticeRedirectionResponseDto(recruitmentNotice.getRecruitmentNoticeId(),
                recruitmentNotice.getJobOfferTitle(),
                recruitmentNotice.getCompanyCode(),
                recruitmentNotice.getStartAt(),
                recruitmentNotice.getEndAt());
    }
}