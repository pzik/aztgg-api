package com.aztgg.api.recruitmentnotice.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecruitmentNoticeRepository extends JpaRepository<RecruitmentNotice, Long>, RecruitmentNoticeCustomRepository {

    List<RecruitmentNotice> findAllByCompanyCode(String companyCode);

    @Modifying
    @Query("UPDATE RecruitmentNotice r SET r.clickCount = r.clickCount + 1 WHERE r.recruitmentNoticeId = :recruitmentNoticeId")
    void incrementClickCountByRecruitmentNoticeId(@Param("recruitmentNoticeId") Long recruitmentNoticeId);
}
