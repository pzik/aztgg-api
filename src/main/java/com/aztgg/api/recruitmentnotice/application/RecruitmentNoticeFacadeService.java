package com.aztgg.api.recruitmentnotice.application;

import com.aztgg.api.global.exception.CommonErrorCode;
import com.aztgg.api.global.exception.CommonException;
import com.aztgg.api.hotissue.domain.HotIssue;
import com.aztgg.api.hotissue.domain.HotIssueDomainService;
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
public class RecruitmentNoticeFacadeService { // TODO : 최신 컨벤션 형태로 리팩토링

    private final HotIssueDomainService hotIssueDomainService;
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

        HotIssue hotIssue = hotIssueDomainService.findHotIssueByRecruitmentNoticeId(recruitmentNoticeId)
                .orElseGet(() -> HotIssue.builder()
                        .recruitmentNoticeId(recruitmentNoticeId)
                        .build());
        hotIssueDomainService.commentToRecruitmentNotice(hotIssue, ip, payload.anonymousName(), payload.content());
    }

    public GetHotIssueByNoticeIdFacadeResponseDto getHotIssueByNoticeId(Long recruitmentNoticeId) {
        return hotIssueDomainService.findHotIssueByRecruitmentNoticeId(recruitmentNoticeId)
                .map(GetHotIssueByNoticeIdFacadeResponseDto::from)
                .orElseGet(() -> GetHotIssueByNoticeIdFacadeResponseDto.empty(recruitmentNoticeId));
    }
}
