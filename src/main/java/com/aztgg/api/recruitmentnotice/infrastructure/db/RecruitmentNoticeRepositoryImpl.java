package com.aztgg.api.recruitmentnotice.infrastructure.db;

import com.aztgg.api.recruitmentnotice.domain.QRecruitmentNotice;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNotice;
import com.aztgg.api.recruitmentnotice.domain.RecruitmentNoticeCustomRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository
public class RecruitmentNoticeRepositoryImpl extends QuerydslRepositorySupport implements RecruitmentNoticeCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public RecruitmentNoticeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(RecruitmentNotice.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public Page<RecruitmentNotice> findByCompanyCodeAndCategoryLikeIn(String companyCode, String category, Pageable pageable) {
        QRecruitmentNotice qRecruitmentNotice = QRecruitmentNotice.recruitmentNotice;

        // where build
        BooleanBuilder whereBooleanBuilder = new BooleanBuilder();

        // companyCode 조회
        if (Objects.nonNull(companyCode)) {
            whereBooleanBuilder.and(qRecruitmentNotice.companyCode.like(companyCode));
        }

        // category 조회
        if (Objects.nonNull(category)) {
            whereBooleanBuilder.and(qRecruitmentNotice.categories.contains(category));
        }

        // 페이징 관련 //

        // total count query
        JPAQuery<Long> countJpaQuery = jpaQueryFactory.select(qRecruitmentNotice.countDistinct())
                .from(qRecruitmentNotice)
                .where(whereBooleanBuilder);
        long totalCount = countJpaQuery.fetch().getFirst();

        // ids query and ids result query
        JPAQuery<Long> pagingIdsQuery = jpaQueryFactory.select(qRecruitmentNotice.recruitmentNoticeId)
                .from(qRecruitmentNotice)
                .where(whereBooleanBuilder);
        List<Long> pagingIds = Objects.requireNonNull(getQuerydsl())
                .applyPagination(pageable, pagingIdsQuery)
                .fetch();

        JPAQuery<RecruitmentNotice> query = jpaQueryFactory.select(qRecruitmentNotice)
                .from(qRecruitmentNotice)
                .where(qRecruitmentNotice.recruitmentNoticeId.in(pagingIds));

        // 정렬 조건 적용
        addOrderBy(qRecruitmentNotice, query, pageable.getSort());

        return new PageImpl<>(query.fetch(), pageable, totalCount);
    }

    private void addOrderBy(QRecruitmentNotice qRecruitmentNotice, JPAQuery<RecruitmentNotice> query, Sort sort) {
        for (Sort.Order order : sort) {
            PathBuilder pathBuilder = new PathBuilder(RecruitmentNotice.class, qRecruitmentNotice.getMetadata().getName());
            query.orderBy(new OrderSpecifier<>(
                    order.isAscending() ? Order.ASC : Order.DESC,
                    pathBuilder.get(order.getProperty())
            ));
        }
    }
}
