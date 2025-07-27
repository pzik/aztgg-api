package com.aztgg.api.board.presentation;

import com.aztgg.api.board.application.BoardFacadeService;
import com.aztgg.api.board.application.dto.*;
import com.aztgg.api.global.security.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/boards")
public class BoardController implements BoardApi {

    private final BoardFacadeService boardFacadeService;

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize( "hasRole('USER')")
    public void createBoard(@AuthenticationPrincipal CustomUserPrincipal principal, @Valid @RequestBody BoardCreateRequestDto payload) {
        boardFacadeService.createBoard(principal.getUserId(), payload);
    }

    @PutMapping("/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize( "hasRole('USER')")
    public void updateBoard(@AuthenticationPrincipal CustomUserPrincipal principal, @PathVariable Long boardId, @Valid @RequestBody BoardUpdateRequestDto payload) {
        boardFacadeService.updateBoard(principal.getUserId(), boardId, payload);
    }

    @Override
    @DeleteMapping("/{boardId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize( "hasRole('USER')")
    public void deleteBoard(@AuthenticationPrincipal CustomUserPrincipal principal, @PathVariable Long boardId) {
        boardFacadeService.deleteBoard(principal.getUserId(), boardId);
    }

    @Override
    @GetMapping("/{boardId}")
    public BoardWithNicknameResponseDto getBoardWithUsername(@PathVariable Long boardId) {
        return boardFacadeService.getBoardWithUsername(boardId);
    }

    @Override
    @GetMapping("/best-item")
    public BoardWithNicknameResponseDto getBestBoardWithUsername() {
        return boardFacadeService.getBestBoardWithUsername();
    }

    @Override
    @GetMapping("")
    public BoardWithNicknameListResponseDto getBoardWithUsernameList(@RequestParam int page,
                                                                     @RequestParam int pageSize,
                                                                     @RequestParam(required = false) String searchWord) {
        return boardFacadeService.getBoardWithUsernameList(page, pageSize, searchWord);
    }

    @Override
    @PostMapping("/{boardId}/like-toggle")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize( "hasRole('USER')")
    public void boardLikeToggle(@AuthenticationPrincipal CustomUserPrincipal principal, @PathVariable Long boardId) {
        boardFacadeService.toggleBoardLike(principal.getUserId(), boardId);
    }

    @Override
    @PostMapping("/{boardId}/comments")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize( "hasRole('USER')")
    public void createCommentToBoard(@AuthenticationPrincipal CustomUserPrincipal principal, @PathVariable Long boardId, @Valid @RequestBody BoardCommentCreateRequestDto payload) {
        boardFacadeService.addCommentToBoard(principal.getUserId(), boardId, payload);
    }

    @Override
    @PutMapping("/{boardId}/comments/{boardCommentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize( "hasRole('USER')")
    public void updateBoardComment(@AuthenticationPrincipal CustomUserPrincipal principal, @PathVariable Long boardId, @PathVariable Long boardCommentId, @Valid @RequestBody BoardCommentUpdateRequestDto payload) {
        boardFacadeService.updateBoardComment(principal.getUserId(), boardId, boardCommentId, payload);
    }

    @Override
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{boardId}/comments/{boardCommentId}")
    @PreAuthorize( "hasRole('USER')")
    public void deleteBoardComment(@AuthenticationPrincipal CustomUserPrincipal principal, @PathVariable Long boardId, @PathVariable Long boardCommentId) {
        boardFacadeService.deleteBoardComment(principal.getUserId(), boardId, boardCommentId);
    }
}
