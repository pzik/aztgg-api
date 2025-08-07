package com.aztgg.api.board.application.dto;


import com.aztgg.api.board.domain.Board;
import com.aztgg.api.board.domain.BoardComment;
import com.aztgg.api.board.domain.BoardLike;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record BoardWithNicknameResponseDto(Long boardId,
                                           Long userId,
                                           String nickname,
                                           List<BoardWithNicknameCommentDto> comments,
                                           List<BoardLikeWithNicknameDto> likes,
                                           String title,
                                           String content,
                                           LocalDateTime createdAt,
                                           LocalDateTime modifiedAt) {

    @Builder
    public record BoardWithNicknameCommentDto(Long boardCommentId,
                                              Long userId,
                                              String nickname,
                                              String content,
                                              LocalDateTime createdAt,
                                              LocalDateTime modifiedAt) {

        public static BoardWithNicknameCommentDto of(BoardComment boardComment, String nickname) {
            return BoardWithNicknameCommentDto.builder()
                    .boardCommentId(boardComment.getBoardCommentId())
                    .userId(boardComment.getUserId())
                    .nickname(nickname)
                    .content(boardComment.getContent())
                    .createdAt(boardComment.getCreatedAt())
                    .modifiedAt(boardComment.getModifiedAt())
                    .build();
        }
    }

    @Builder
    public record BoardLikeWithNicknameDto(Long boardLikeId,
                                           Long userId,
                                           String nickname,
                                           LocalDateTime createdAt,
                                           LocalDateTime modifiedAt) {

        public static BoardLikeWithNicknameDto of(BoardLike boardLike, String nickname) {
            return BoardLikeWithNicknameDto.builder()
                    .boardLikeId(boardLike.getBoardLikeId())
                    .userId(boardLike.getUserId())
                    .nickname(nickname)
                    .createdAt(boardLike.getCreatedAt())
                    .modifiedAt(boardLike.getModifiedAt())
                    .build();
        }
    }

    public static BoardWithNicknameResponseDto of(Board board,
                                                  String nickname,
                                                  List<BoardWithNicknameCommentDto> comments,
                                                  List<BoardLikeWithNicknameDto> likes) {
        return BoardWithNicknameResponseDto.builder()
                .boardId(board.getBoardId())
                .userId(board.getUserId())
                .nickname(nickname)
                .comments(comments)
                .likes(likes)
                .title(board.getTitle())
                .content(board.getContent())
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .build();
    }
}
