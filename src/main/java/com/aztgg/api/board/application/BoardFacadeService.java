package com.aztgg.api.board.application;

import com.aztgg.api.auth.domain.User;
import com.aztgg.api.auth.domain.UserDomainService;
import com.aztgg.api.auth.domain.exception.InvalidUsernameDomainException;
import com.aztgg.api.board.application.dto.*;
import com.aztgg.api.board.application.exception.BoardErrorCode;
import com.aztgg.api.board.domain.Board;
import com.aztgg.api.board.domain.BoardComment;
import com.aztgg.api.board.domain.BoardDomainService;
import com.aztgg.api.board.domain.BoardLike;
import com.aztgg.api.global.exception.CommonErrorCode;
import com.aztgg.api.global.exception.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardFacadeService {

    private final BoardDomainService boardDomainService;
    private final UserDomainService userDomainService;

    @Transactional(readOnly = true)
    public BoardWithNicknameListResponseDto getBoardWithUsernameList(int page, int pageSize, String searchWord) {
        Page<Board> boardList = boardDomainService.findAll(page, pageSize, searchWord);
        List<BoardWithNicknameResponseDto> result = boardList.stream()
                .map(this::boardEntityToResponseDto)
                .toList();
        return BoardWithNicknameListResponseDto.of(result, boardList.getTotalElements());
    }

    public BoardWithNicknameResponseDto getBoardWithUsername(Long boardId) {
        return boardDomainService.findById(boardId)
                .map(this::boardEntityToResponseDto)
                .orElseThrow(() -> new CommonException(CommonErrorCode.BAD_REQUEST, "not found board"));
    }

    public BoardWithNicknameResponseDto getBestBoardWithUsername() {
        Board board = boardDomainService.getWeeklyBestBoard().orElseGet(() -> null);
        if (board == null) {
            return null;
        }
        return boardEntityToResponseDto(board);
    }

    public void createBoard(String username, BoardCreateRequestDto payload) {
        Long userId;
        try {
            userId = userDomainService.findUserByUsername(username).getId();
        } catch (InvalidUsernameDomainException e) {
            throw new CommonException(BoardErrorCode.INVALID_USER);
        }
        boardDomainService.createBoard(userId, payload.title(), payload.content());
    }

    public void updateBoard(String username, Long boardId, BoardUpdateRequestDto payload) {
        Long userId;
        try {
            userId = userDomainService.findUserByUsername(username).getId();
        } catch (InvalidUsernameDomainException e) {
            throw new CommonException(BoardErrorCode.INVALID_USER);
        }

        Board board = boardDomainService.findById(boardId)
                .orElseThrow(() -> new CommonException(CommonErrorCode.BAD_REQUEST, "not found board"));
        if (!board.getUserId().equals(userId)) {
            throw new CommonException(BoardErrorCode.INVALID_USER);
        }

        board.updateTitle(payload.title());
        board.updateContent(payload.content());
    }

    public void deleteBoard(String username, Long boardId) {
        Long userId;
        try {
            userId = userDomainService.findUserByUsername(username).getId();
        } catch (InvalidUsernameDomainException e) {
            throw new CommonException(BoardErrorCode.INVALID_USER);
        }

        Board board = boardDomainService.findById(boardId)
                .orElseThrow(() -> new CommonException(CommonErrorCode.BAD_REQUEST, "not found board"));
        if (!board.getUserId().equals(userId)) {
            throw new CommonException(BoardErrorCode.INVALID_USER);
        }
        boardDomainService.deleteBoard(board.getBoardId());
    }

    public void toggleBoardLike(String username, Long boardId) {
        Long userId;
        try {
            userId = userDomainService.findUserByUsername(username).getId();
        } catch (InvalidUsernameDomainException e) {
            throw new CommonException(BoardErrorCode.INVALID_USER);
        }

        Board board = boardDomainService.findById(boardId)
                .orElseThrow(() -> new CommonException(CommonErrorCode.BAD_REQUEST, "not found board"));
        Optional<BoardLike> originLike = board.getMyLike(userId);

        if (originLike.isEmpty()) {
            boardDomainService.increaseWeeklyLikeCount(boardId);
            board.addLike(userId);
        } else {
            board.removeLike(userId);
            // 좋아요가 현재 주차에 한 경우 주차별 좋아요도 취소
            BoardLike boardLike = originLike.get();
            WeekFields weekFields = WeekFields.of(Locale.KOREA);

            int curWeekOfYear = LocalDateTime.now().get(weekFields.weekOfYear());
            if (boardLike.getCreatedAt().get(weekFields.weekOfYear()) == curWeekOfYear) {
                boardDomainService.decreaseWeeklyLikeCount(boardId, curWeekOfYear);
            }
        }
    }

    public void addCommentToBoard(String username, Long boardId, BoardCommentCreateRequestDto payload) {
        Long userId;
        try {
            userId = userDomainService.findUserByUsername(username).getId();
        } catch (InvalidUsernameDomainException e) {
            throw new CommonException(BoardErrorCode.INVALID_USER);
        }

        Board board = boardDomainService.findById(boardId)
                .orElseThrow(() -> new CommonException(CommonErrorCode.BAD_REQUEST, "not found board"));

        board.addComment(userId, payload.content());
    }

    public void updateBoardComment(String username, Long boardId, Long boardCommentId, BoardCommentUpdateRequestDto payload) {
        Board board = boardDomainService.findById(boardId)
                .orElseThrow(() -> new CommonException(CommonErrorCode.BAD_REQUEST, "not found board"));

        try {
            Long userId = userDomainService.findUserByUsername(username).getId();
            BoardComment boardComment = board.getCommentByCommentId(boardCommentId);
            if (!boardComment.getUserId().equals(userId)) {
                throw new CommonException(BoardErrorCode.INVALID_USER);
            }

            boardComment.updateComment(payload.content());
        } catch (InvalidUsernameDomainException e) {
            throw new CommonException(BoardErrorCode.INVALID_USER);
        } catch (IllegalStateException e) {
            throw new CommonException(CommonErrorCode.BAD_REQUEST);
        }
    }

    public void deleteBoardComment(String username, Long boardId, Long boardCommentId) {
        Board board = boardDomainService.findById(boardId)
                .orElseThrow(() -> new CommonException(CommonErrorCode.BAD_REQUEST, "not found board"));

        try {
            Long userId = userDomainService.findUserByUsername(username).getId();
            BoardComment boardComment = board.getCommentByCommentId(boardCommentId);
            if (!boardComment.getUserId().equals(userId)) {
                throw new CommonException(BoardErrorCode.INVALID_USER);
            }
            board.deleteCommentById(boardCommentId);
        } catch (InvalidUsernameDomainException e) {
            throw new CommonException(BoardErrorCode.INVALID_USER);
        } catch (IllegalStateException e) {
            throw new CommonException(CommonErrorCode.BAD_REQUEST);
        }
    }

    private BoardWithNicknameResponseDto boardEntityToResponseDto(Board board) {
        Map<Long, String> userNicknames = userDomainService.findAllUsersByIds(board.findAllRelatedUserId()).stream()
                .collect(Collectors.toMap(
                        User::getId,   // key mapper
                        User::getNickname   // value mapper
                ));

        List<BoardWithNicknameResponseDto.BoardWithNicknameCommentDto> commentDtoList = board.getComments().stream()
                .map(a -> BoardWithNicknameResponseDto.BoardWithNicknameCommentDto.of(a, userNicknames.getOrDefault(a.getUserId(), board.getDefaultUsername())))
                .toList();
        List<BoardWithNicknameResponseDto.BoardLikeWithNicknameDto> likeDtoList = board.getLikes().stream()
                .map(a -> BoardWithNicknameResponseDto.BoardLikeWithNicknameDto.of(a, userNicknames.getOrDefault(a.getUserId(), board.getDefaultUsername())))
                .toList();

        return BoardWithNicknameResponseDto.of(board, userNicknames.getOrDefault(board.getUserId(), board.getDefaultUsername()), commentDtoList, likeDtoList);
    }
}
