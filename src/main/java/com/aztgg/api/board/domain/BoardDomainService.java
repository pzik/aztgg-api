package com.aztgg.api.board.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BoardDomainService {

    private final BoardRepository boardRepository;

    public void createBoard(Long userId, String title, String content) {
        Board board = Board.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .build();

        boardRepository.save(board);
    }

    public void deleteBoard(Long boardId) {
        boardRepository.deleteById(boardId);
    }

    public void increaseWeeklyLikeCount(Long boardId) {
        LocalDateTime localDateTime = LocalDateTime.now();
        WeekFields weekFields = WeekFields.of(Locale.KOREA);
        int weekOfYear = localDateTime.get(weekFields.weekOfYear());
        boardRepository.increaseWeeklyLikeCount(boardId, weekOfYear);
    }

    public void decreaseWeeklyLikeCount(Long boardId, int weekOfYear) {
        boardRepository.decreaseWeeklyLikeCount(boardId, weekOfYear);
    }

    @Transactional(readOnly = true)
    public Optional<Board> findById(Long boardId) {
        return boardRepository.findById(boardId);
    }

    @Transactional(readOnly = true)
    public Page<Board> findAll(int page, int pageSize, String searchWord) {
        PageRequest pageRequest = PageRequest.of(page, pageSize,
                Sort.by(Sort.Direction.fromString("desc"), "createdAt"));
        return boardRepository.findAllBySearchWordPageable(pageRequest, searchWord);
    }

    @Transactional(readOnly = true)
    public Optional<Board> getWeeklyBestBoard() {
        WeekFields weekFields = WeekFields.of(Locale.KOREA);
        int weekOfYear = LocalDateTime.now().get(weekFields.weekOfYear());

        List<Board> boardWeeklyList = boardRepository.getBoardRankByWeekOfYear(weekOfYear);
        if (!boardWeeklyList.isEmpty()) {
            return Optional.of(boardWeeklyList.getFirst());
        }
        return Optional.empty();
    }
}
