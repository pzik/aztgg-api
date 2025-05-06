package com.aztgg.api.recruitmentnotice.application.dto;

import com.aztgg.api.company.domain.CompanyConstants;
import com.aztgg.api.company.domain.PredefinedCompany;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNotice;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Set;

public record GetRecruitmentNoticeRedirectionResponseDto(Long recruitmentNoticeId,
                                                         String jobOfferTitle,
                                                         String companyCode,
                                                         String companyName,
                                                         String url,
                                                         Set<String> categories,
                                                         int clickCount,
                                                         @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") LocalDateTime startAt,
                                                         @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") LocalDateTime endAt) {

    public static GetRecruitmentNoticeRedirectionResponseDto from(RecruitmentNotice recruitmentNotice) {
        String companyName;
        try {
            companyName = PredefinedCompany.valueOf(recruitmentNotice.getCompanyCode()).getKorean();
        } catch (Exception e) {
            companyName = CompanyConstants.UNKNOWN_NAME;
        }

        return new GetRecruitmentNoticeRedirectionResponseDto(recruitmentNotice.getRecruitmentNoticeId(),
                recruitmentNotice.getJobOfferTitle(),
                recruitmentNotice.getCompanyCode(),
                companyName,
                recruitmentNotice.getUrl(),
                StringUtils.commaDelimitedListToSet(recruitmentNotice.getCategories()),
                recruitmentNotice.getClickCount(),
                recruitmentNotice.getStartAt(),
                recruitmentNotice.getEndAt());
    }
}