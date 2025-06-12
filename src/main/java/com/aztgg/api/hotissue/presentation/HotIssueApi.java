package com.aztgg.api.hotissue.presentation;

import com.aztgg.api.hotissue.application.dto.CreateCommentToHotIssueRequestDto;
import com.aztgg.api.hotissue.application.dto.GetHotIssueResponseDto;
import com.aztgg.api.hotissue.application.dto.GetHotIssuesResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "HotIssue", description = "HotIssue API")
@ApiResponses(value = {
        @ApiResponse(
                responseCode = "400",
                description = "BAD_REQUEST",
                content = @Content
        ),
        @ApiResponse(
                responseCode = "500",
                description = "INTERNAL_SERVER_ERROR",
                content = @Content
        ),
})
public interface HotIssueApi {

        @Operation(tags = {"HotIssue"}, summary = "HotIssue 항목 조회", description = """
            ## API 설명
            hotIssueId로 핫이슈 항목을 상세 조회합니다.
            """)
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK"),
        })
        GetHotIssueResponseDto getHotIssue(Long hotIssueId);

        @Operation(tags = {"HotIssue"}, summary = "HotIssue 식별자로 핫이슈 코멘트 생성", description = """
            ## API 설명
            hotIssueId를 이용해 핫이슈에 코멘트를 답니다.
            """)
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK"),
        })
        void addComment(HttpServletRequest httpServletRequest, Long hotIssueId, CreateCommentToHotIssueRequestDto payload);

        @Operation(tags = {"HotIssue"}, summary = "HotIssue 활성화 항목 조회", description = """
            ## API 설명
            startAt, endAt 사이에 있는 활성화 핫이슈 항목들을 조회합니다.
            
            댓글은 5개까지만 보여줍니다.
            """)
        @ApiResponses(value = {
                @ApiResponse(
                        responseCode = "200",
                        description = "OK"),
        })
        GetHotIssuesResponseDto getActivateHotIssues();
}
