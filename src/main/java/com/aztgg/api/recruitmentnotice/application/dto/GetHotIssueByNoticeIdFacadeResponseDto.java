package com.aztgg.api.recruitmentnotice.application.dto;

import com.aztgg.api.hotissue.domain.HotIssue;
import com.aztgg.api.hotissue.domain.HotIssueComment;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
public record GetHotIssueByNoticeIdFacadeResponseDto(Long hotIssueId,
                                                     Long recruitmentNoticeId,
                                                     List<GetHotIssueByNoticeIdResponseCommentDto> comments) {

    @Builder
    public record GetHotIssueByNoticeIdResponseCommentDto(Long hotIssueCommentId,
                                                String maskedIp,
                                                String anonymousName,
                                                String content,
                                                LocalDateTime createdAt,
                                                LocalDateTime modifiedAt) {
        public static GetHotIssueByNoticeIdResponseCommentDto from(HotIssueComment comment) {
            return GetHotIssueByNoticeIdResponseCommentDto.builder()
                    .hotIssueCommentId(comment.getHotIssueCommentId())
                    .maskedIp(comment.getMaskedIp())
                    .anonymousName(comment.getAnonymousName())
                    .content(comment.getContent())
                    .createdAt(comment.getCreatedAt())
                    .modifiedAt(comment.getModifiedAt())
                    .build();
        }
    }

    public static GetHotIssueByNoticeIdFacadeResponseDto from(HotIssue hotIssue) {
        List<GetHotIssueByNoticeIdResponseCommentDto> list = hotIssue.getComments().stream()
                .map(GetHotIssueByNoticeIdResponseCommentDto::from)
                .toList();

        return GetHotIssueByNoticeIdFacadeResponseDto.builder()
                .hotIssueId(hotIssue.getHotIssueId())
                .recruitmentNoticeId(hotIssue.getRecruitmentNoticeId())
                .comments(list)
                .build();
    }

    public static GetHotIssueByNoticeIdFacadeResponseDto empty(Long recruitmentNoticeId) {
        List<GetHotIssueByNoticeIdResponseCommentDto> list = new ArrayList<>();

        return GetHotIssueByNoticeIdFacadeResponseDto.builder()
                .recruitmentNoticeId(recruitmentNoticeId)
                .comments(list)
                .build();
    }
}
