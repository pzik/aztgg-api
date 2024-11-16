package com.aztgg.api.recruitmentnotice.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecruitmentNoticeRepository extends JpaRepository<RecruitmentNotice, Long> {

    Page<RecruitmentNotice> findByCompanyCodeInOrderByStartAtDesc(List<String> companyCodes, Pageable pageable);
}
