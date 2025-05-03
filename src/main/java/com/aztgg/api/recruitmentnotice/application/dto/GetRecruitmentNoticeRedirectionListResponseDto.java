package com.aztgg.api.recruitmentnotice.application.dto;

import com.aztgg.api.recruitmentnotice.domain.RecruitmentNotice;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

public record GetRecruitmentNoticeRedirectionListResponseDto(List<GetRecruitmentNoticeRedirectionResponseDto> list,
                                                             MetadataDto metadata) {

    public record MetadataDto(long totalElements) {

    }

    public static GetRecruitmentNoticeRedirectionListResponseDto from(Page<RecruitmentNotice> page) {
        List<GetRecruitmentNoticeRedirectionResponseDto> list = page.getContent().stream()
                .map(GetRecruitmentNoticeRedirectionResponseDto::from)
                .toList();

        MetadataDto metadataDto = new MetadataDto(page.getTotalElements());

        return new GetRecruitmentNoticeRedirectionListResponseDto(list, metadataDto);
    }
}
