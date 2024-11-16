package com.aztgg.api.recruitmentnotice.application.dto;

import com.aztgg.api.recruitmentnotice.domain.RecruitmentNotice;

import java.util.Collection;
import java.util.List;

public record GetRecruitmentNoticeRedirectionListResponseDto(List<GetRecruitmentNoticeRedirectionResponseDto> list) {

    public static GetRecruitmentNoticeRedirectionListResponseDto from(Collection<RecruitmentNotice> recruitmentNotices) {
        List<GetRecruitmentNoticeRedirectionResponseDto> list = recruitmentNotices.stream()
                .map(GetRecruitmentNoticeRedirectionResponseDto::from)
                .toList();

        return new GetRecruitmentNoticeRedirectionListResponseDto(list);
    }
}
