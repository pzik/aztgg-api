package com.aztgg.api.recruitmentnotice.application.dto;

import com.aztgg.api.recruitmentnotice.domain.RecruitmentNotice;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Set;

public record GetRecruitmentNoticeRedirectionResponseDto(Long recruitmentNoticeId,
                                                         String jobOfferTitle,
                                                         String companyCode,
                                                         Set<String> categories,
                                                         @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") LocalDateTime startAt,
                                                         @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") LocalDateTime endAt) {

    public static GetRecruitmentNoticeRedirectionResponseDto from(RecruitmentNotice recruitmentNotice) {
        return new GetRecruitmentNoticeRedirectionResponseDto(recruitmentNotice.getRecruitmentNoticeId(),
                recruitmentNotice.getJobOfferTitle(),
                recruitmentNotice.getCompanyCode(),
                StringUtils.commaDelimitedListToSet(recruitmentNotice.getCategories()),
                recruitmentNotice.getStartAt(),
                recruitmentNotice.getEndAt());
    }
}