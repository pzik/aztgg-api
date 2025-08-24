package com.aztgg.api.hotissue.domain;

import com.aztgg.api.hotissue.domain.exception.HotIssueNotFoundDomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class HotIssueDomainService {

    private final HotIssueRepository hotIssueRepository;

    public void commentToHotIssue(Long hotIssueId, String ip, String anonymousName, String content) {
        HotIssue hotIssue = hotIssueRepository.findById(hotIssueId)
                .orElseThrow(HotIssueNotFoundDomainException::new);
        hotIssue.addComment(ip, anonymousName, content);
    }

    public void commentToRecruitmentNotice(HotIssue hotIssue, String ip, String anonymousName, String content) {
        hotIssue.addComment(ip, anonymousName, content);
        hotIssueRepository.save(hotIssue);
    }

    @Transactional(readOnly = true)
    public List<HotIssue> getActivatedHotIssues() {
        LocalDateTime now = LocalDateTime.now();
        return hotIssueRepository.findAllByStartAtLessThanEqualAndEndAtGreaterThanEqual(now, now)
                .stream()
                .toList();
    }

    public Optional<HotIssue> findHotIssueById(Long hotIssueId) {
        return hotIssueRepository.findById(hotIssueId);
    }

    public Optional<HotIssue> findHotIssueByRecruitmentNoticeId(Long recruitmentNoticeId) {
        return hotIssueRepository.findOneByRecruitmentNoticeId(recruitmentNoticeId);
    }
}
