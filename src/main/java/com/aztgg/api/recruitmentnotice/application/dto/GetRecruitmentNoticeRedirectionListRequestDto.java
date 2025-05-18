package com.aztgg.api.recruitmentnotice.application.dto;

import com.aztgg.api.global.exception.CommonErrorCode;
import com.aztgg.api.global.exception.CommonException;
import lombok.Builder;
import org.springframework.util.CollectionUtils;

import java.util.List;

public record GetRecruitmentNoticeRedirectionListRequestDto(String companyCode,
                                                            String category,
                                                            int page,
                                                            int pageSize,
                                                            List<String> sort) {

    @Builder
    public GetRecruitmentNoticeRedirectionListRequestDto {
        if (CollectionUtils.isEmpty(sort) || sort.size() != 2) {
            throw new CommonException(CommonErrorCode.BAD_REQUEST, "invalid sort");
        }
    }
}
