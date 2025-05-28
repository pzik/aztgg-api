package com.aztgg.api.recruitmentnotice.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface RecruitmentNoticeCustomRepository {

    Page<RecruitmentNotice> findByCompanyCodeLikeAndCategoryInAndStandardCategoryLike(String companyCode, String category, String standardCategory, Pageable pageable);

    void increaseDailyNoticeClickCount(Long recruitmentNoticeId, LocalDateTime localDateTime);

    List<Long> getRecruitmentNoticesByDailyRank(String date, int size);
}
