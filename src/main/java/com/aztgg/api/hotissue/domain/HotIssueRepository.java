package com.aztgg.api.hotissue.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface HotIssueRepository extends JpaRepository<HotIssue, Long> {

    Optional<HotIssue> findOneByRecruitmentNoticeId(Long recruitmentNoticeId);

    List<HotIssue> findAllByStartAtLessThanEqualAndEndAtGreaterThanEqual(LocalDateTime targetTime1, LocalDateTime targetTime2);
}
