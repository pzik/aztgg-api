package com.aztgg.api.hotissue.application.dto;

import com.aztgg.api.hotissue.domain.HotIssue;
import com.aztgg.api.hotissue.domain.HotIssueComment;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record GetHotIssueResponseDto(Long hotIssueId,
                                     Long recruitmentNoticeId,
                                     String title,
                                     String content,
                                     List<GetHotIssueResponseCommentDto> comments,
                                     LocalDateTime startAt,
                                     LocalDateTime endAt,
                                     LocalDateTime createdAt,
                                     LocalDateTime modifiedAt) {

    @Builder
    public record GetHotIssueResponseCommentDto(Long hotIssueCommentId,
                                                String maskedIp,
                                                String anonymousName,
                                                String content,
                                                LocalDateTime createdAt,
                                                LocalDateTime modifiedAt) {
        public static GetHotIssueResponseCommentDto from(HotIssueComment hotIssueComment) {
            return GetHotIssueResponseCommentDto.builder()
                    .hotIssueCommentId(hotIssueComment.getHotIssueCommentId())
                    .anonymousName(hotIssueComment.getAnonymousName())
                    .maskedIp(hotIssueComment.getMaskedIp())
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
                .title(hotIssue.getTitle())
                .content(hotIssue.getContent())
                .recruitmentNoticeId(hotIssue.getRecruitmentNoticeId())
                .comments(commentDtoList)
                .startAt(hotIssue.getStartAt())
                .endAt(hotIssue.getEndAt())
                .createdAt(hotIssue.getCreatedAt())
                .modifiedAt(hotIssue.getModifiedAt())
                .build();
    }

    public static GetHotIssueResponseDto fromLimitCommentDesc(HotIssue hotIssue, int limitCount) {
        List<GetHotIssueResponseCommentDto> commentDtoList = hotIssue.getComments()
                .stream()
                .map(GetHotIssueResponseCommentDto::from)
                .limit(limitCount)
                .toList();
        return GetHotIssueResponseDto.builder()
                .hotIssueId(hotIssue.getHotIssueId())
                .title(hotIssue.getTitle())
                .content(hotIssue.getContent())
                .recruitmentNoticeId(hotIssue.getRecruitmentNoticeId())
                .comments(commentDtoList)
                .startAt(hotIssue.getStartAt())
                .endAt(hotIssue.getEndAt())
                .createdAt(hotIssue.getCreatedAt())
                .modifiedAt(hotIssue.getModifiedAt())
                .build();
    }
}
