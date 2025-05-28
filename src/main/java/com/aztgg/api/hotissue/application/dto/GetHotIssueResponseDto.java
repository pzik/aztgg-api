package com.aztgg.api.hotissue.application.dto;

import com.aztgg.api.hotissue.domain.HotIssue;
import com.aztgg.api.hotissue.domain.HotIssueComment;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record GetHotIssueResponseDto(Long hotIssueId,
                                     Long recruitmentNoticeId,
                                     List<GetHotIssueResponseCommentDto> comments,
                                     String title,
                                     String content,
                                     LocalDateTime createdAt,
                                     LocalDateTime modifiedAt) {

    @Builder
    public record GetHotIssueResponseCommentDto(Long hotIssueCommentId,
                                                String ip,
                                                String anonymousName,
                                                String content,
                                                LocalDateTime createdAt,
                                                LocalDateTime modifiedAt) {
        public static GetHotIssueResponseCommentDto from(HotIssueComment hotIssueComment) {
            return GetHotIssueResponseCommentDto.builder()
                    .hotIssueCommentId(hotIssueComment.getHotIssueCommentId())
                    .ip(hotIssueComment.getIp())
                    .anonymousName(hotIssueComment.getAnonymousName())
                    .content(hotIssueComment.getContent())
                    .createdAt(hotIssueComment.getCreatedAt())
                    .modifiedAt(hotIssueComment.getModifiedAt())
                    .build();
        }
    }

    public static GetHotIssueResponseDto from(HotIssue hotIssue) {
        List<GetHotIssueResponseCommentDto> commentDtoList = hotIssue.getComments().stream()
                .map(GetHotIssueResponseCommentDto::from)
                .toList();
        return GetHotIssueResponseDto.builder()
                .hotIssueId(hotIssue.getHotIssueId())
                .recruitmentNoticeId(hotIssue.getRecruitmentNoticeId())
                .comments(commentDtoList)
                .title(hotIssue.getTitle())
                .content(hotIssue.getContent())
                .createdAt(hotIssue.getCreatedAt())
                .modifiedAt(hotIssue.getModifiedAt())
                .build();
    }
}
