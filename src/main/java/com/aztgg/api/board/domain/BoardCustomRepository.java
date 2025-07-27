package com.aztgg.api.board.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardCustomRepository {

    Page<Board> findAllBySearchWordPageable(Pageable pageable, String searchWord);

    List<Board> getBoardRankByWeekOfYear(int weekOfYear);

    void increaseWeeklyLikeCount(Long boardId, int weekOfYear);

    void decreaseWeeklyLikeCount(Long boardId, int weekOfYear);
}
