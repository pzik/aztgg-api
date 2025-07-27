package com.aztgg.api.board.presentation;

import com.aztgg.api.board.application.dto.*;
import com.aztgg.api.global.security.CustomUserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Board", description = "Board API")
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
public interface BoardApi {

    @Operation(tags = {"Board"}, summary = "Board 생성", description = """
            ## API 설명
            커뮤니티 게시글 생성 요청을 합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    void createBoard(CustomUserPrincipal principal, BoardCreateRequestDto payload);

    @Operation(tags = {"Board"}, summary = "Board 수정", description = """
            ## API 설명
            커뮤니티 게시글을 수정합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    void updateBoard(CustomUserPrincipal principal, Long boardId, BoardUpdateRequestDto payload);

    @Operation(tags = {"Board"}, summary = "Board 삭제", description = """
            ## API 설명
            커뮤니티 게시글을 삭제합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    void deleteBoard(CustomUserPrincipal principal, Long boardId);

    @Operation(tags = {"Board"}, summary = "Board 조회", description = """
            ## API 설명
            커뮤니티 게시글을 단건 조회 합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    BoardWithNicknameResponseDto getBoardWithUsername(Long boardId);

    @Operation(tags = {"Board"}, summary = "베스트 Board 조회", description = """
            ## API 설명
            이번 주 가장 인기있는 게시글을 조회합니다.
            
            만약 이번 주 좋아요가 많은 게시글이 없다면?
            저번 주 인기 게시글을 가져옵니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    BoardWithNicknameResponseDto getBestBoardWithUsername();

    @Operation(tags = {"Board"}, summary = "Board 복수 조회", description = """
            ## API 설명
            커뮤니티 게시글을 복수 조회 합니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    BoardWithNicknameListResponseDto getBoardWithUsernameList(int page,
                                                              int pageSize,
                                                              String searchWord);

    @Operation(tags = {"Board"}, summary = "Board 좋아요 토글", description = """
            ## API 설명
            커뮤니티 게시글에 좋아요를 누르거나 취소합니다. (토글방식)
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    void boardLikeToggle(CustomUserPrincipal principal, Long boardId);

    @Operation(tags = {"Board"}, summary = "Board 댓글달기", description = """
            ## API 설명
            커뮤니티 게시글에 댓글을 답니다.
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    void createCommentToBoard(CustomUserPrincipal principal, Long boardId, BoardCommentCreateRequestDto payload);

    @Operation(tags = {"Board"}, summary = "Board 댓글 수정", description = """
            ## API 설명
            커뮤니티 게시글 댓글을 수정합니다
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    void updateBoardComment(CustomUserPrincipal principal, Long boardId, Long boardCommentId, BoardCommentUpdateRequestDto payload);

    @Operation(tags = {"Board"}, summary = "Board 댓글 삭제", description = """
            ## API 설명
            커뮤니티 게시글 댓글을 삭제합니다
            """)
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "OK"),
    })
    void deleteBoardComment(CustomUserPrincipal principal, Long boardId, Long boardCommentId);
}
