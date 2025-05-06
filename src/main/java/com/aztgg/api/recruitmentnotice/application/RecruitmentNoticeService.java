package com.aztgg.api.recruitmentnotice.application;

import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeRedirectionListResponseDto;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNotice;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RecruitmentNoticeService {

    private final RecruitmentNoticeRepository recruitmentNoticeRepository;

    @Transactional(readOnly = true)
    public GetRecruitmentNoticeRedirectionListResponseDto getRecruitmentNoticeRedirectionList(String companyCode,
                                                                                              String category,
                                                                                              PageRequest pageRequest) {
        Page<RecruitmentNotice> result = recruitmentNoticeRepository.findByCompanyCodeAndCategoryLikeInOrderByStartAtDesc(companyCode, category, pageRequest);
        return GetRecruitmentNoticeRedirectionListResponseDto.from(result);
    }

    @Transactional
    public void incrementClickCountByRecruitmentNoticeId(Long recruitmentNoticeId) {
        recruitmentNoticeRepository.incrementClickCountByRecruitmentNoticeId(recruitmentNoticeId);
    }
}
