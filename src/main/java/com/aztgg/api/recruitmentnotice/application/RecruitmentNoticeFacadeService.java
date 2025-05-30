package com.aztgg.api.recruitmentnotice.application;

import com.aztgg.api.global.exception.CommonErrorCode;
import com.aztgg.api.global.exception.CommonException;
import com.aztgg.api.hotissue.application.HotIssueService;
import com.aztgg.api.recruitmentnotice.application.dto.CreateHotIssueCommentByRecruitmentNoticeIdRequestDto;
import com.aztgg.api.recruitmentnotice.application.dto.GetRecruitmentNoticeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RecruitmentNoticeFacadeService {

    private final HotIssueService hotIssueService;
    private final RecruitmentNoticeService recruitmentNoticeService;

    public void createComment(Long recruitmentNoticeId, String ip, CreateHotIssueCommentByRecruitmentNoticeIdRequestDto payload) {
        if (!StringUtils.hasText(ip)) {
            throw new CommonException(CommonErrorCode.BAD_REQUEST, "invalid ip");
        }

        GetRecruitmentNoticeResponseDto notice = recruitmentNoticeService.findRecruitmentNoticeRedirectionById(recruitmentNoticeId)
                .orElseGet(() -> null);
        if (Objects.isNull(notice)) {
            throw new CommonException(CommonErrorCode.BAD_REQUEST, "invalid recruitmentNoticeId");
        }

        hotIssueService.commentToRecruitmentNotice(recruitmentNoticeId, ip, payload.content());
    }
    // TODO : 조회 기능 필요
}
