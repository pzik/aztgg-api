package com.aztgg.api.recruitmentnotice.application.dto;

import com.aztgg.api.global.asset.PredefinedCorporate;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNotice;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record GetRecruitmentNoticeResponseDto(Long recruitmentNoticeId,
                                              String jobOfferTitle,
                                              String companyCode,
                                              String url,
                                              Set<String> categories,
                                              String standardCategory,
                                              List<CorporateDto> corporates,
                                              int clickCount,
                                              @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") LocalDateTime startAt,
                                              @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'") LocalDateTime endAt) {

    public record CorporateDto(String corporateCode, String corporateName) {

        public static CorporateDto from(PredefinedCorporate corporate) {
            return new CorporateDto(corporate.name(), corporate.getKorean());
        }
    }

    public static GetRecruitmentNoticeResponseDto from(RecruitmentNotice recruitmentNotice) {
        List<CorporateDto> corporateDtoList = StringUtils.commaDelimitedListToSet(recruitmentNotice.getCorporateCodes()).stream()
                .map(PredefinedCorporate::fromCode)
                .map(CorporateDto::from)
                .toList();

        return new GetRecruitmentNoticeResponseDto(recruitmentNotice.getRecruitmentNoticeId(),
                recruitmentNotice.getJobOfferTitle(),
                recruitmentNotice.getCompanyCode(),
                recruitmentNotice.getUrl(),
                StringUtils.commaDelimitedListToSet(recruitmentNotice.getCategories()),
                recruitmentNotice.getStandardCategory(),
                corporateDtoList,
                recruitmentNotice.getClickCount(),
                recruitmentNotice.getStartAt(),
                recruitmentNotice.getEndAt());
    }
}