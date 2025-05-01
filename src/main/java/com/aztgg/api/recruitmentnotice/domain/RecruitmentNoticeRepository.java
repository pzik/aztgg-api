package com.aztgg.api.recruitmentnotice.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentNoticeRepository extends JpaRepository<RecruitmentNotice, Long>, RecruitmentNoticeCustomRepository {

    List<RecruitmentNotice> findAllByCompanyCode(String companyCode);
}
