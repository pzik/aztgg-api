package com.aztgg.api.recruitmentnotice.application;

import com.aztgg.api.global.exception.CommonErrorCode;
import com.aztgg.api.global.exception.CommonException;
import com.aztgg.api.hotissue.application.HotIssueService;
import com.aztgg.api.recruitmentnotice.application.dto.CreateHotIssueCommentByRecruitmentNoticeIdFacadeRequestDto;
import com.aztgg.api.recruitmentnotice.application.dto.GetHotIssueByNoticeIdFacadeResponseDto;
import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class RecruitmentNoticeFacadeService {

    private final HotIssueService hotIssueService;
    private final RecruitmentNoticeService recruitmentNoticeService;

    public void createComment(Long recruitmentNoticeId, String ip, CreateHotIssueCommentByRecruitmentNoticeIdFacadeRequestDto payload) {
        if (!StringUtils.hasText(ip)) {
            throw new CommonException(CommonErrorCode.BAD_REQUEST, "invalid ip");
        }

        GetRecruitmentNoticeResponseDto notice = recruitmentNoticeService.findRecruitmentNoticeRedirectionById(recruitmentNoticeId)
                .orElseGet(() -> null);
        if (Objects.isNull(notice)) {
            throw new CommonException(CommonErrorCode.BAD_REQUEST, "invalid recruitmentNoticeId");
        }

        hotIssueService.commentToRecruitmentNotice(recruitmentNoticeId, ip, payload.anonymousName(), payload.content());
    }

    public GetHotIssueByNoticeIdFacadeResponseDto getHotIssueByNoticeId(Long recruitmentNoticeId) {
        try {
            return GetHotIssueByNoticeIdFacadeResponseDto.from(hotIssueService.getHotIssueByRecruitmentNoticeId(recruitmentNoticeId));
        } catch (CommonException e) {
            return GetHotIssueByNoticeIdFacadeResponseDto.empty(recruitmentNoticeId);
        }
    }
}
