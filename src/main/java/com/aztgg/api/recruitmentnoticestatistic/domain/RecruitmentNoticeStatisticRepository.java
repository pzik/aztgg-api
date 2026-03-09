package com.aztgg.api.recruitmentnoticestatistic.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RecruitmentNoticeStatisticRepository extends JpaRepository<RecruitmentNoticeStatistic, Long> {

    @Query("SELECT s FROM RecruitmentNoticeStatistic s " +
            "WHERE s.createdAt BETWEEN :startAt AND :endAt " +
            "AND (:standardCategory IS NULL OR s.standardCategory = :standardCategory)")
    List<RecruitmentNoticeStatistic> findAllByCondition(
            @Param("startAt") LocalDate startAt,
            @Param("endAt") LocalDate endAt,
            @Param("standardCategory") String standardCategory
    );
}
