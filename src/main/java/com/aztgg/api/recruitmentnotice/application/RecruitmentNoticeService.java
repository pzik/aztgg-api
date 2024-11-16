package com.aztgg.api.recruitmentnotice.application;

import com.aztgg.api.global.exception.CommonException;
import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeRedirectionListResponseDto;
import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeRedirectionResponseDto;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNotice;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNoticeErrorCode;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecruitmentNoticeService {

    private final RecruitmentNoticeRepository recruitmentNoticeRepository;

    @Transactional
    public GetRecruitmentNoticeRedirectionResponseDto incrementViewCountAndGetRecruitmentNoticeRedirection(Long recruitmentNoticeId) {
        RecruitmentNotice recruitmentNotice = recruitmentNoticeRepository.findById(recruitmentNoticeId)
                .orElseThrow(() -> new CommonException(RecruitmentNoticeErrorCode.BAD_REQUEST_RECRUITMENT_NOTICE_NOT_FOUND));

        recruitmentNotice.increaseCount();
        return GetRecruitmentNoticeRedirectionResponseDto.from(recruitmentNotice);
    }

    @Transactional(readOnly = true)
    public GetRecruitmentNoticeRedirectionListResponseDto getRecruitmentNoticeRedirectionList(List<String> companyCodes,
                                                                                              PageRequest pageRequest) {
        List<RecruitmentNotice> result = recruitmentNoticeRepository.findByCompanyCodeInOrderByStartAtDesc(companyCodes, pageRequest)
                .getContent();
        return GetRecruitmentNoticeRedirectionListResponseDto.from(result);
    }
}
