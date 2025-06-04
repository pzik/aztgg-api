package com.aztgg.api.hotissue.application;

import com.aztgg.api.global.exception.CommonErrorCode;
import com.aztgg.api.global.exception.CommonException;
import com.aztgg.api.hotissue.application.dto.GetHotIssueResponseDto;
import com.aztgg.api.hotissue.domain.HotIssue;
import com.aztgg.api.hotissue.domain.HotIssueComment;
import com.aztgg.api.hotissue.domain.HotIssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HotIssueService {

    private final HotIssueRepository hotIssueRepository;

    @Transactional
    public void commentToRecruitmentNotice(Long recruitmentNoticeId, String ip, String content) {
        if (content.length() > 500) {
            throw new CommonException(CommonErrorCode.BAD_REQUEST, "Comments can only be up to 500 characters.");
        }
        HotIssueComment hotIssueComment = HotIssueComment.builder()
                .ip(ip)
                .content(content)
                .build();

        HotIssue hotIssue = hotIssueRepository.findOneByRecruitmentNoticeId(recruitmentNoticeId)
                .orElseGet(() -> HotIssue.builder()
                        .recruitmentNoticeId(recruitmentNoticeId)
                        .build());

        hotIssue.addComment(hotIssueComment);
        hotIssueRepository.save(hotIssue);
    }

    public GetHotIssueResponseDto getHotIssueByRecruitmentNoticeId(Long recruitmentNoticeId) {
        HotIssue hotIssue = hotIssueRepository.findOneByRecruitmentNoticeId(recruitmentNoticeId)
                .orElseThrow(() -> new CommonException(CommonErrorCode.BAD_REQUEST, "not found hotIssue"));
        return GetHotIssueResponseDto.from(hotIssue);
    }
}
