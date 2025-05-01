package com.aztgg.api.recruitmentnotice.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RecruitmentNoticeCustomRepository {

    Page<RecruitmentNotice> findByCompanyCodeAndCategoryLikeInOrderByStartAtDesc(String companyCode, String category, Pageable pageable);
}
