package com.aztgg.api.hotissue.application;

import com.aztgg.api.global.exception.CommonErrorCode;
import com.aztgg.api.global.exception.CommonException;
import com.aztgg.api.hotissue.application.dto.CreateCommentToHotIssueRequestDto;
import com.aztgg.api.hotissue.application.dto.GetHotIssueResponseDto;
import com.aztgg.api.hotissue.application.dto.GetHotIssuesResponseDto;
import com.aztgg.api.hotissue.domain.HotIssue;
import com.aztgg.api.hotissue.domain.HotIssueComment;
import com.aztgg.api.hotissue.domain.HotIssueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class HotIssueService {

    private final HotIssueRepository hotIssueRepository;

    @Transactional
    public void commentToHotIssue(Long hotIssueId, String ip, CreateCommentToHotIssueRequestDto payload) {
        HotIssue hotIssue = hotIssueRepository.findById(hotIssueId)
                .orElseThrow(() -> new CommonException(CommonErrorCode.BAD_REQUEST, "invalid hotIssueId"));
        addComment(hotIssue, ip, payload.anonymousName(), payload.content());
    }

    @Transactional
    public void commentToRecruitmentNotice(Long recruitmentNoticeId, String ip, String anonymousName, String content) {
        HotIssue hotIssue = hotIssueRepository.findOneByRecruitmentNoticeId(recruitmentNoticeId)
                .orElseGet(() -> HotIssue.builder()
                        .recruitmentNoticeId(recruitmentNoticeId)
                        .build());

        addComment(hotIssue, ip, anonymousName, content);
        hotIssueRepository.save(hotIssue);
    }

    public GetHotIssuesResponseDto getActivatedHotIssues() {
        LocalDateTime now = LocalDateTime.now();
        List<GetHotIssueResponseDto> hotIssues = hotIssueRepository.findAllByStartAtLessThanEqualAndEndAtGreaterThanEqual(now, now)
                .stream()
                .map(a -> GetHotIssueResponseDto.fromLimitComment(a, 5))
                .toList();
        return new GetHotIssuesResponseDto(hotIssues);
    }

    public GetHotIssueResponseDto getHotIssueById(Long hotIssueId) {
        HotIssue hotIssue = hotIssueRepository.findById(hotIssueId)
                .orElseThrow(() -> new CommonException(CommonErrorCode.BAD_REQUEST, "not found hotIssue"));
        return GetHotIssueResponseDto.from(hotIssue);
    }

    public GetHotIssueResponseDto getHotIssueByRecruitmentNoticeId(Long recruitmentNoticeId) {
        HotIssue hotIssue = hotIssueRepository.findOneByRecruitmentNoticeId(recruitmentNoticeId)
                .orElseThrow(() -> new CommonException(CommonErrorCode.BAD_REQUEST, "not found hotIssue"));
        return GetHotIssueResponseDto.from(hotIssue);
    }

    private void addComment(HotIssue hotIssue, String ip, String anonymousName, String content) {
        HotIssueComment hotIssueComment = HotIssueComment.builder()
                .ip(ip)
                .content(content)
                .anonymousName(anonymousName)
                .build();

        hotIssue.addComment(hotIssueComment);
    }
}
