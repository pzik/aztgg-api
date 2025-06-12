package com.aztgg.api.recruitmentnotice.application.dto;

import com.aztgg.api.hotissue.application.dto.GetHotIssueResponseDto;
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
        public static GetHotIssueByNoticeIdResponseCommentDto from(GetHotIssueResponseDto.GetHotIssueResponseCommentDto commentDto) {
            return GetHotIssueByNoticeIdResponseCommentDto.builder()
                    .hotIssueCommentId(commentDto.hotIssueCommentId())
                    .maskedIp(commentDto.maskedIp())
                    .anonymousName(commentDto.anonymousName())
                    .content(commentDto.content())
                    .createdAt(commentDto.createdAt())
                    .modifiedAt(commentDto.modifiedAt())
                    .build();
        }
    }

    public static GetHotIssueByNoticeIdFacadeResponseDto from(GetHotIssueResponseDto dto) {
        List<GetHotIssueByNoticeIdResponseCommentDto> list = dto.comments().stream()
                .map(GetHotIssueByNoticeIdResponseCommentDto::from)
                .toList();

        return GetHotIssueByNoticeIdFacadeResponseDto.builder()
                .hotIssueId(dto.hotIssueId())
                .recruitmentNoticeId(dto.recruitmentNoticeId())
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
