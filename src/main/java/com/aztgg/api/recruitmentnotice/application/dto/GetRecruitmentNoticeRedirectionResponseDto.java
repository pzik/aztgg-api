package com.aztgg.api.recruitmentnotice.application.dto;

import com.aztgg.api.recruitmentnotice.domain.RecruitmentNotice;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record GetRecruitmentNoticeRedirectionResponseDto(String jobOfferTitle,
                                                         String companyCode,
                                                         String url,
                                                         @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") LocalDateTime startAt,
                                                         @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") LocalDateTime endAt) {

    public static GetRecruitmentNoticeRedirectionResponseDto from(RecruitmentNotice recruitmentNotice) {
        return new GetRecruitmentNoticeRedirectionResponseDto(recruitmentNotice.getJobOfferTitle(),
                recruitmentNotice.getCompanyCode(),
                recruitmentNotice.getUrl(),
                recruitmentNotice.getStartAt(),
                recruitmentNotice.getEndAt());
    }
}
