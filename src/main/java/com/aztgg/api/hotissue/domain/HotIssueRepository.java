package com.aztgg.api.hotissue.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface HotIssueRepository extends JpaRepository<HotIssue, Long> {

    Optional<HotIssue> findOneByRecruitmentNoticeId(Long recruitmentNoticeId);
}
