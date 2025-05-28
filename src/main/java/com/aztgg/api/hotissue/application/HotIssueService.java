package com.aztgg.api.hotissue.application;

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
        String anonymousName = "익명";

        HotIssueComment hotIssueComment = HotIssueComment.builder()
                .anonymousName(anonymousName)
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
}
