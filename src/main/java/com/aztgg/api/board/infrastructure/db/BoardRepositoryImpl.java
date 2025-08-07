package com.aztgg.api.board.infrastructure.db;

import com.aztgg.api.board.domain.Board;
import com.aztgg.api.board.domain.BoardCustomRepository;
import com.aztgg.api.board.domain.QBoard;
import com.aztgg.api.global.util.QuerydslUtil;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
public class BoardRepositoryImpl extends QuerydslRepositorySupport implements BoardCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final StringRedisTemplate stringRedisTemplate;

    private static final String WEEKLY_BOARD_CLICK_COUNT = "dclick:board:%s"; // year, weekOfYear

    public BoardRepositoryImpl(JPAQueryFactory jpaQueryFactory, StringRedisTemplate stringRedisTemplate) {
        super(Board.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Page<Board> findAllBySearchWordPageable(Pageable pageable, String searchWord) {
        QBoard qBoard = QBoard.board;
        // where build
        BooleanBuilder whereBooleanBuilder = new BooleanBuilder();

        if(StringUtils.hasText(searchWord)) {
            whereBooleanBuilder.and(qBoard.title.like(searchWord));
        }

        // total count query
        JPAQuery<Long> countJpaQuery = jpaQueryFactory.select(qBoard.countDistinct())
                .from(qBoard)
                .where(whereBooleanBuilder);
        long totalCount = countJpaQuery.fetch().getFirst();

        // ids query and ids result query
        JPAQuery<Long> pagingIdsQuery = jpaQueryFactory.select(qBoard.boardId)
                .from(qBoard)
                .where(whereBooleanBuilder);
        List<Long> pagingIds = Objects.requireNonNull(getQuerydsl())
                .applyPagination(pageable, pagingIdsQuery)
                .fetch();

        JPAQuery<Board> query = jpaQueryFactory.select(qBoard)
                .from(qBoard)
                .where(qBoard.boardId.in(pagingIds));

        // 정렬 조건 적용
        QuerydslUtil.addOrderBy(Board.class, qBoard, query, pageable.getSort());

        return new PageImpl<>(query.fetch(), pageable, totalCount);
    }

    @Override
    public List<Board> getBoardRankByWeekOfYear(int weekOfYear) {
        String key = String.format(WEEKLY_BOARD_CLICK_COUNT, weekOfYear);

        Set<String> ids = stringRedisTemplate.opsForZSet().reverseRange(key, 0, 99);

        if (CollectionUtils.isEmpty(ids)) {
            return new ArrayList<>();
        }

        List<Long> longIds = ids.stream().map(Long::parseLong).toList();

        QBoard qBoard = QBoard.board;
        return jpaQueryFactory.select(qBoard)
                .from(qBoard)
                .where(qBoard.boardId.in(longIds))
                .fetch();
    }

    @Override
    public void increaseWeeklyLikeCount(Long boardId, int weekOfYear) {
        String key = String.format(WEEKLY_BOARD_CLICK_COUNT, weekOfYear);

        stringRedisTemplate.opsForZSet().incrementScore(key, String.valueOf(boardId), 1);

        // TTL 설정 (마지막 호출일자로부터 3주일간 무조건 설정)
        stringRedisTemplate.expire(key, Duration.ofDays(21));
    }

    @Override
    public void decreaseWeeklyLikeCount(Long boardId, int weekOfYear) {
        String key = String.format(WEEKLY_BOARD_CLICK_COUNT, weekOfYear);
        stringRedisTemplate.opsForZSet().incrementScore(key, String.valueOf(boardId), -1);

        // TTL 설정 (마지막 호출일자로부터 3주일간 무조건 설정)
        stringRedisTemplate.expire(key, Duration.ofDays(21));
    }
}
